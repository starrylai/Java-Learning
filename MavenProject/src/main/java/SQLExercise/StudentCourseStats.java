package SQLExercise;

import java.sql.*;
import java.util.Random;

public class StudentCourseStats {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/school?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "urPassword"; 

        // 1. 插入足够的数据（确保有 ≥10 个学生选课数 ≥2）
        insertSampleData(url, user, password);

        // 2. 执行查询：每位学生所选课程数 ≥2，按数量倒序分页，输出前10行
        queryTopStudents(url, user, password);
    }

    /**
     * 插入示例数据：30名学生，8门课程，随机分配选课
     */
    public static void insertSampleData(String url, String user, String password) {
        final int STUDENT_COUNT = 30;       // 学生总数
        final int COURSE_COUNT = 8;         // 课程总数
        final int MIN_COURSES_PER_STUDENT = 1;   // 最少选课数
        final int MAX_COURSES_PER_STUDENT = 7;   // 最多选课数

        Random rand = new Random();

        String insertStudent = "INSERT INTO student (name, enrolled_date) VALUES (?, ?)";
        String insertCourse = "INSERT INTO course (name) VALUES (?)";
        String insertEnrollment = "INSERT INTO enrollment (student_id, course_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false); // 开启事务，提高插入性能

            // 插入学生
            try (PreparedStatement pstmt = conn.prepareStatement(insertStudent, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 1; i <= STUDENT_COUNT; i++) {
                    pstmt.setString(1, "学生" + i);
                    pstmt.setDate(2, Date.valueOf("2023-09-01"));
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                System.out.println(STUDENT_COUNT + " 名学生插入成功");
            }

            // 插入课程
            try (PreparedStatement pstmt = conn.prepareStatement(insertCourse, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 1; i <= COURSE_COUNT; i++) {
                    pstmt.setString(1, "课程" + i);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                System.out.println(COURSE_COUNT + " 门课程插入成功");
            }

            // 获取所有学生的ID和所有课程的ID（用于随机分配）
            // 注意：我们假设 student 和 course 表中的 id 是自增的，插入后可以查询得到
            int[] studentIds = new int[STUDENT_COUNT];
            int[] courseIds = new int[COURSE_COUNT];

            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT id FROM student ORDER BY id");
                int idx = 0;
                while (rs.next()) {
                    studentIds[idx++] = rs.getInt("id");
                }

                rs = stmt.executeQuery("SELECT id FROM course ORDER BY id");
                idx = 0;
                while (rs.next()) {
                    courseIds[idx++] = rs.getInt("id");
                }
            }

            // 随机分配选课：每个学生随机选择 [MIN, MAX] 门不同的课程
            try (PreparedStatement pstmt = conn.prepareStatement(insertEnrollment)) {
                int totalEnrollments = 0;
                for (int studentId : studentIds) {
                    int courseCount = rand.nextInt(MAX_COURSES_PER_STUDENT - MIN_COURSES_PER_STUDENT + 1)
                            + MIN_COURSES_PER_STUDENT; // 随机选课数量
                    // 随机打乱课程ID顺序，取前 courseCount 个，保证不重复
                    int[] shuffled = courseIds.clone();
                    for (int i = 0; i < shuffled.length; i++) {
                        int j = rand.nextInt(shuffled.length);
                        int tmp = shuffled[i];
                        shuffled[i] = shuffled[j];
                        shuffled[j] = tmp;
                    }
                    for (int i = 0; i < courseCount; i++) {
                        pstmt.setInt(1, studentId);
                        pstmt.setInt(2, shuffled[i]);
                        pstmt.addBatch();
                        totalEnrollments++;
                        // 每500条执行一次批量提交
                        if (totalEnrollments % 500 == 0) {
                            pstmt.executeBatch();
                        }
                    }
                }
                pstmt.executeBatch(); // 提交剩余批次
                System.out.println("选课记录插入成功，共 " + totalEnrollments + " 条");
            }

            conn.commit(); // 提交事务
            System.out.println("数据插入完成。");

        } catch (SQLException e) {
            e.printStackTrace();
            // 如果出错，回滚事务
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 查询每位学生所选课程数 ≥2，按数量倒序，取前10行
     */
    public static void queryTopStudents(String url, String user, String password) {
        String sql = "SELECT s.id, s.name, COUNT(e.course_id) AS cnt " +
                "FROM student s " +
                "LEFT JOIN enrollment e ON s.id = e.student_id " +
                "GROUP BY s.id, s.name " +
                "HAVING cnt >= 2 " +
                "ORDER BY cnt DESC " +
                "LIMIT 10";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== 选课数 ≥2 的前10名学生 ===");
            System.out.printf("%-6s %-10s %s%n", "id", "name", "cnt");
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int cnt = rs.getInt("cnt");
                System.out.printf("%-6d %-10s %d%n", id, name, cnt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}