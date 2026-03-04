package SQLExercise;

import java.sql.*;
import java.util.Random;

public class IndexComparison {

    private static final String URL = "jdbc:mysql://localhost:3306/school?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "urPassword";

    public static void main(String[] args) {
        // 批量插入
//        int insertRecord = 200000;
//        int BATCH_SIZE = 1000;
//        insertStudents(insertRecord, BATCH_SIZE);

        // 1. 无索引查询
        System.out.println("=== 无索引查询 ===");
        queryAndExplain(false);

        // 2. 创建索引
        System.out.println();
        createIndex();

        // 3. 有索引查询
        System.out.println("\n=== 有索引查询 ===");
        queryAndExplain(true);

        // 4. 可选：删除索引（清理环境）
         dropIndex();
    }

    /** 确保表中有足够数据 */
    static void insertStudents(int total, int BATCH_SIZE) {
        String sql = "INSERT INTO student (name, enrolled_date) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // 关闭自动提交，开启事务
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                Random rand = new Random();

                for (int i = 1; i <= total; i++) {
                    // 生成姓名：Student1, Student2, ...
                    String name = "Student" + i;

                    // 生成随机入学日期：2000-01-01 之后的随机天数（0~7999天）
                    long randomDays = rand.nextInt(8000); // 0~7999
                    Date enrolledDate = Date.valueOf("2000-01-01");
                    // 使用 Calendar 或 LocalDate 计算新日期
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(enrolledDate);
                    cal.add(java.util.Calendar.DAY_OF_YEAR, (int) randomDays);
                    java.sql.Date sqlDate = new java.sql.Date(cal.getTimeInMillis());

                    pstmt.setString(1, name);
                    pstmt.setDate(2, sqlDate);
                    pstmt.addBatch();

                    // 每 BATCH_SIZE 条执行一次批量提交
                    if (i % BATCH_SIZE == 0) {
                        pstmt.executeBatch();
                        conn.commit(); // 提交事务
                        System.out.println("已插入 " + i + " 条记录");
                    }
                }

                // 插入剩余不足一批的数据
                pstmt.executeBatch();
                conn.commit();
                System.out.println("插入完成，共 " + total + " 条记录");

            } catch (SQLException e) {
                // 出错时回滚事务
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 执行查询并打印耗时和explain */
    static void queryAndExplain(boolean withIndex) {
        String querySql = "SELECT * FROM student WHERE name = 'Student100000'";
        String explainSql = "EXPLAIN " + querySql;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // 记录开始时间
            long start = System.currentTimeMillis();

            // 执行查询
            ResultSet rs = stmt.executeQuery(querySql);
            int count = 0;
            while (rs.next()) {
                count++;
            }
            long end = System.currentTimeMillis();
            System.out.println("查询耗时: " + (end - start) + " ms, 返回行数: " + count);

            // 获取执行计划
            ResultSet planRs = stmt.executeQuery(explainSql);
            ResultSetMetaData meta = planRs.getMetaData();
            int colCount = meta.getColumnCount();

            System.out.println("EXPLAIN 输出:");
            while (planRs.next()) {
                for (int i = 1; i <= colCount; i++) {
                    System.out.printf("%s: %s  ", meta.getColumnName(i), planRs.getString(i));
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 在 name 列上创建索引 */
    static void createIndex() {
        String sql = "CREATE INDEX idx_student_name ON student(name)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("索引 idx_student_name 创建成功。");
        } catch (SQLException e) {
            System.out.println("索引可能已存在: " + e.getMessage());
        }
    }

    static void dropIndex() {
        String sql = "DROP INDEX idx_student_name ON student";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("\n索引已删除。");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}