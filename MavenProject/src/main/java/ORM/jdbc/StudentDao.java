package ORM.jdbc;

import ORM.entity.Student;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private DataSource dataSource;

    public StudentDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Create
    public void insert(Student student) throws SQLException {
        String sql = "INSERT INTO student (name, age, email) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getEmail());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    student.setId(rs.getLong(1));
                }
            }
        }
    }

    // Read by id
    public Student findById(Long id) throws SQLException {
        String sql = "SELECT id, name, age, email FROM student WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getLong("id"));
                    student.setName(rs.getString("name"));
                    student.setAge(rs.getInt("age"));
                    student.setEmail(rs.getString("email"));
                    return student;
                }
            }
        }
        return null;
    }

    // Read all
    public List<Student> findAll() throws SQLException {
        String sql = "SELECT id, name, age, email FROM student";
        List<Student> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getLong("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setEmail(rs.getString("email"));
                list.add(student);
            }
        }
        return list;
    }

    // Update
    public void update(Student student) throws SQLException {
        String sql = "UPDATE student SET name = ?, age = ?, email = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getEmail());
            ps.setLong(4, student.getId());
            ps.executeUpdate();
        }
    }

    // Delete
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM student WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}