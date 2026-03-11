package ORM.OneToManyExample.Mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootApplication
@Transactional
public class Application implements CommandLineRunner {
    @Autowired
    private StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<Student> students = studentService.findAllWithCourses();
        students.forEach(s -> {
            System.out.println("学生：" + s.getName());
            s.getEnrollments().forEach(e ->
                    System.out.println("  课程：" + e.getCourse().getName()));
        });

        int spId = 3;
        Student idStudent = studentService.findByIdWithCourses(spId);
        System.out.println("id:"+spId);
        System.out.println("学生：" + idStudent.getName());
        idStudent.getEnrollments().forEach(e ->
                System.out.println("  课程：" + e.getCourse().getName()));
    }
}

