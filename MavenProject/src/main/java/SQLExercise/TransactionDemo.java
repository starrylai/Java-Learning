package SQLExercise;

import java.sql.*;
import java.util.concurrent.CountDownLatch;

public class TransactionDemo {

    private static final String URL = "jdbc:mysql://localhost:3306/school?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "urPassword";

    public static void main(String[] args) {
        // 先重置数据
        resetData();

        // 脏读（Read Uncommitted 可能发生）

        // 演示不可重复读（Read Committed 可避免，Repeatable Read 可避免）
        System.out.println("******不可重复读演示******");
        demoNonRepeatableRead();

        // 演示幻读（RC可能发生，RR可能发生但InnoDB通过间隙锁防止幻读）
//        System.out.println("******幻读演示******");
//        demoPhantomRead();
    }

    /** 重置 student 表，保证 id=1 的数据为已知状态 */
    static void resetData() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("UPDATE student SET name = '张三' WHERE id = 1");
            System.out.println("数据已重置：id=1 -> 张三");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 演示不可重复读：在 RR 级别下不会发生，在 RC 级别下会发生 */
    static void demoNonRepeatableRead() {
        // 线程1：先读取，然后等待，再读取
        Thread t1 = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.setAutoCommit(false);
                // 设置隔离级别为 Read Committed 以观察不可重复读；可改为 REPEATABLE READ 观察区别
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                // conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

                // 第一次读取
                String name1 = queryNameById(conn, 1);
                System.out.println("T1 第一次读取 id=1 姓名: " + name1);

                // 等待 T2 修改并提交
                Thread.sleep(3000);

                // 第二次读取
                String name2 = queryNameById(conn, 1);
                System.out.println("T1 第二次读取 id=1 姓名: " + name2);

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 线程2：修改 id=1 的姓名并提交
        Thread t2 = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.setAutoCommit(false);
                // T2 使用默认隔离级别即可
                PreparedStatement pstmt = conn.prepareStatement("UPDATE student SET name = '李四' WHERE id = 1");
                pstmt.executeUpdate();
                System.out.println("T2 修改 id=1 姓名为李四");
                conn.commit();
                System.out.println("T2 提交事务");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        t2.start();
    }

    /** 演示幻读（在 READ COMMITTED 级别下发生） */
    static void demoPhantomRead() {
        // 清理所有表，避免外键干扰
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM enrollment");
            stmt.executeUpdate("DELETE FROM student");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 插入初始数据，id 1-5 和 20-25，中间留下 6-19 的空隙
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO student (id, name, enrolled_date) VALUES (?, ?, ?)")) {
            conn.setAutoCommit(true);
            for (int id = 1; id <= 5; id++) {
                pstmt.setInt(1, id);
                pstmt.setString(2, "学生" + id);
                pstmt.setDate(3, Date.valueOf("2023-01-01"));
                pstmt.addBatch();
            }
            for (int id = 20; id <= 25; id++) {
                pstmt.setInt(1, id);
                pstmt.setString(2, "学生" + id);
                pstmt.setDate(3, Date.valueOf("2023-01-01"));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("初始数据插入完成：id 1-5, 20-25");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CountDownLatch latch = new CountDownLatch(1); // 控制 T1 第一次查询后通知 T2

        Thread t1 = new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.setAutoCommit(false);
                // 使用 READ COMMITTED 以便观察到新插入的行（幻读）
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                String sql = "SELECT id, name FROM student WHERE id BETWEEN 10 AND 20 ORDER BY id";
                // 第一次查询
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    System.out.println("T1第一次查询 id 10-20 结果：");
                    boolean hasData = false;
                    while (rs.next()) {
                        hasData = true;
                        System.out.println("   id=" + rs.getInt("id") + ", name=" + rs.getString("name"));
                    }
                    if (!hasData) System.out.println("   无数据");
                }

                latch.countDown(); // 通知 T2 可以插入

                Thread.sleep(3000); // 等待 T2 插入并提交

                // 第二次查询
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    System.out.println("T1第二次查询 id 10-20 结果：");
                    while (rs.next()) {
                        System.out.println("   id=" + rs.getInt("id") + ", name=" + rs.getString("name"));
                    }
                }

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                latch.await(); // 等待 T1 第一次查询完成
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.setAutoCommit(false);
                try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO student (id, name, enrolled_date) VALUES (15, '幻影学生', '2024-01-01')")) {
                    pstmt.executeUpdate();
                    System.out.println("T2插入 id=15 的记录");
                }
                conn.commit();
                System.out.println("T2提交插入");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static String queryNameById(Connection conn, int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT name FROM student WHERE id = ?")) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
            return null;
        }
    }
}