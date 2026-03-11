package ORM.service;

import ORM.PageResult;
import ORM.entity.StudentJPA;
import ORM.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HttpStudentService {
    @Autowired
    private final StudentRepository studentRepository;

    public HttpStudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public PageResult<StudentJPA> searchByName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentJPA> studentPage = studentRepository.findByNameContainingIgnoreCase(keyword, pageable);

        return new PageResult<>(
                studentPage.getContent(),
                studentPage.getTotalElements(),
                studentPage.getNumber(),
                studentPage.getSize()
        );
    }
}
