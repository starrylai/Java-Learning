package ORM.OneToManyExample.Mybatis;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Transactional(readOnly = true)
    public List<Student> findAllWithCourses() {
        return studentMapper.selectAllWithCourses();
    }

    @Transactional(readOnly = true)
    public Student findByIdWithCourses(int id) {
        return studentMapper.selectByIdWithCourses(id);
    }
}