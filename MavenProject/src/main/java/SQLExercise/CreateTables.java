package SQLExercise;

import java.sql.*;

public class CreateTables {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/school?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "urPassword";

        // 建表语句
        String createStudent = "CREATE TABLE IF NOT EXISTS student (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(64) NOT NULL," +
                "enrolled_date DATE NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        String createCourse = "CREATE TABLE IF NOT EXISTS course (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(128) NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        String createEnrollment = "CREATE TABLE IF NOT EXISTS enrollment (" +
                "student_id BIGINT NOT NULL," +
                "course_id BIGINT NOT NULL," +
                "PRIMARY KEY(student_id, course_id)," +
                "CONSTRAINT fk_enr_stu FOREIGN KEY(student_id) REFERENCES student(id) ON DELETE CASCADE," +
                "CONSTRAINT fk_enr_cou FOREIGN KEY(course_id) REFERENCES course(id) ON DELETE CASCADE" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // 1. 创建表
            stmt.execute(createStudent);
            stmt.execute(createCourse);
            stmt.execute(createEnrollment);
            System.out.println("表创建成功（或已存在）");

            // 2. 安全创建索引（先检查是否存在）
            // 课程名唯一索引
            createIndexIfNotExists(conn, "course", "uq_course_name",
                    "CREATE UNIQUE INDEX uq_course_name ON course(name)");

            // 学生姓名前缀索引（前10个字符）
            createIndexIfNotExists(conn, "student", "idx_student_name_prefix",
                    "CREATE INDEX idx_student_name_prefix ON student(name(10))");

            createIndexIfNotExists(conn, "student", "idx_student_name_enrolled",
                    "CREATE INDEX idx_student_name_enrolled ON student(name, enrolled_date)");

            System.out.println("索引创建成功（或已存在）");

            // 插入示例数据并测试约束
//            ConstrintsTest(DriverManager.getConnection(url, user, password));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 检查索引是否存在，不存在则创建
     */
    public static void createIndexIfNotExists(Connection conn, String table, String indexName, String createSql) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? AND INDEX_NAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
            pstmt.setString(1, table);
            pstmt.setString(2, indexName);
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                if (rs.getInt(1) == 0) {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(createSql);
                        System.out.println("索引 " + indexName + " 已创建");
                    }
                } else {
                    System.out.println("索引 " + indexName + " 已存在，跳过创建");
                }
            }
        }
    }

    public static void ConstrintsTest(Connection conn) throws SQLException{
        // 插入示例数据并测试约束
        try (Statement stmt = conn.createStatement()) {

            // 插入学生
            stmt.executeUpdate("INSERT INTO student (name, enrolled_date) VALUES ('张三', '2023-09-01')");
            stmt.executeUpdate("INSERT INTO student (name, enrolled_date) VALUES ('李四', '2023-09-01')");

            // 插入课程
            stmt.executeUpdate("INSERT INTO course (name) VALUES ('数学')");
            stmt.executeUpdate("INSERT INTO course (name) VALUES ('英语')");

            // 插入选课记录（正常情况）
            stmt.executeUpdate("INSERT INTO enrollment (student_id, course_id) VALUES (1, 1)");
            stmt.executeUpdate("INSERT INTO enrollment (student_id, course_id) VALUES (1, 2)");

            // 尝试插入重复主键（复合主键重复）—— 会抛出异常
            try {
                stmt.executeUpdate("INSERT INTO enrollment (student_id, course_id) VALUES (1, 1)");
            } catch (SQLException e) {
                System.out.println("预期错误：违反主键唯一性 -> " + e.getMessage());
            }

            // 尝试插入不存在的学生（违反外键）—— 会抛出异常
            try {
                stmt.executeUpdate("INSERT INTO enrollment (student_id, course_id) VALUES (999, 1)");
            } catch (SQLException e) {
                System.out.println("预期错误：违反外键约束 -> " + e.getMessage());
            }

            // 验证唯一索引：尝试插入同名课程
            try {
                stmt.executeUpdate("INSERT INTO course (name) VALUES ('数学')");
            } catch (SQLException e) {
                System.out.println("预期错误：违反唯一索引 -> " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}