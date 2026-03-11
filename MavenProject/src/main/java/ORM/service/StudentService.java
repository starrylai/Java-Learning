package ORM.service;

import ORM.entity.StudentJPA;
import ORM.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public StudentJPA createStudent(StudentJPA student) {
        return studentRepository.save(student);  // 自动返回包含生成ID的对象
    }

    @Transactional(readOnly = true)
    public StudentJPA findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<StudentJPA> findAll() {
        return studentRepository.findAll();
    }

    @Transactional
    public StudentJPA updateStudent(StudentJPA student) {
        // 注意：student 对象必须包含 id，save 会判断是更新还是插入
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<StudentJPA> searchByName(String keyword) {
        return studentRepository.findByNameContaining(keyword);
    }

    // 演示事务回滚的方法，回滚error+Exception的子类，不加rollbackFor只回滚RuntimeException+error
    @Transactional(rollbackFor = Exception.class)
    public void createWithRollback(StudentJPA student) {
        studentRepository.save(student);
        // 故意抛出异常，触发回滚
        throw new RuntimeException("模拟异常，事务回滚");
    }
}
