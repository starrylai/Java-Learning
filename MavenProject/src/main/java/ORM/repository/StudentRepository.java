package ORM.repository;

import ORM.entity.StudentJPA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<StudentJPA, Long> {
    // 派生查询：根据姓名模糊查找
    List<StudentJPA> findByNameContaining(String keyword);
    Page<StudentJPA> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
