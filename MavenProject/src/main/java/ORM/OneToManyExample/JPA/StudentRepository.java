package ORM.OneToManyExample.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // 使用 JOIN FETCH 一次查询加载 enrollments 和 course
    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.course")
    List<Student> findAllWithCourses();

    // 也可以根据学生ID查询
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.course WHERE s.id = :id")
    Student findByIdWithCourses(@Param("id") Long id);
}
