package HTTP;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final IdempotencyService idempotencyService;

    @Transactional(readOnly = true)
    public Page<Student> getAllStudents(Pageable pageable, String name) {
        if (name != null && !name.isEmpty()) {
            // 使用 Specification 实现按姓名模糊过滤
            return studentRepository.findAll((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"), pageable);
        }
        return studentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT_NOT_FOUND", "Student with id " + id + " not found"));
    }

    @Transactional
    public Student createStudent(Student student, String idempotencyKey) {
        // 幂等性检查
        if (idempotencyKey != null && idempotencyService.hasKey(idempotencyKey)) {
            return (Student) idempotencyService.getResult(idempotencyKey);
        }

        try {
            Student saved = studentRepository.save(student);
            if (idempotencyKey != null) {
                idempotencyService.saveResult(idempotencyKey, saved);
            }
            return saved;
        } catch (DataIntegrityViolationException e) {
            // 处理唯一约束冲突（如邮箱重复）
            throw new ConflictException("EMAIL_ALREADY_EXISTS", "Email already exists");
        }
    }

    @Transactional
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        // 使用版本号乐观锁（@Version 会自动处理）
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        // enrollmentDate 不更新，或根据需要更新
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}
