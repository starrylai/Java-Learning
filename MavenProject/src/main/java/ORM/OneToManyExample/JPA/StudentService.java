package ORM.OneToManyExample.JPA;

import ORM.entity.StudentJPA;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<Student> findAllWithCourses() {
        return studentRepository.findAllWithCourses();
    }

    @Transactional(readOnly = true)
    public Student findByIdWithCourses(long id) {
        return studentRepository.findByIdWithCourses(id);
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional
    public List<Student> saveAll(List<Student> students) {
        return studentRepository.saveAll(students);
    }
}
