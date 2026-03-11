package ORM.service;

import ORM.PageResult;
import ORM.entity.Student;
import ORM.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StudentServiceMB {
    private final StudentMapper studentMapper;

    public StudentServiceMB(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentMapper.selectById(id);
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentMapper.selectAll();
    }

    @Transactional
    public void create(Student student) {
        studentMapper.insert(student);
    }

    @Transactional
    public void update(Student student) {
        studentMapper.update(student);
    }

    @Transactional
    public void delete(Long id) {
        studentMapper.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResult<Student> searchByName(String keyword, int page, int size) {
        int offset = page * size;  // 注意：MySQL LIMIT 从 0 开始
        List<Student> list = studentMapper.selectByPage(keyword, offset, size);
        long total = studentMapper.countByName(keyword);
        return new PageResult<>(list, total, page, size);
    }
}
