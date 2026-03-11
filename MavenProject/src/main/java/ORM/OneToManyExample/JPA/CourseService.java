package ORM.OneToManyExample.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
