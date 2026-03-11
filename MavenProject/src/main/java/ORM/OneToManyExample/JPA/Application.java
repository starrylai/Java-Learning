package ORM.OneToManyExample.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Transactional
public class Application implements CommandLineRunner {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Student> allStudents = studentService.findAll();
//        List<Course> allCourses = courseService.findAll();

//        //基于已有student表和course表进行选课
//
//        //给第1个学生选课1、2
//        Student stu1 = allStudents.get(0);
//        Enrollment e1 = new Enrollment();
//        e1.setStudent(stu1);
//        e1.setCourse(allCourses.get(0));
//        stu1.getEnrollments().add(e1);
//
//        Enrollment e2 = new Enrollment();
//        e2.setStudent(stu1);
//        e2.setCourse(allCourses.get(1));
//        stu1.getEnrollments().add(e2);
//
//        //给第2个学生选课2、3
//        Student stu2 = allStudents.get(1);
//        Enrollment e3 = new Enrollment();
//        e3.setStudent(stu2);
//        e3.setCourse(allCourses.get(1));
//        stu2.getEnrollments().add(e3);
//
//        Enrollment e4 = new Enrollment();
//        e4.setStudent(stu2);
//        e4.setCourse(allCourses.get(2));
//        stu2.getEnrollments().add(e4);
//
//        studentService.saveAll(Arrays.asList(stu1, stu2));

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

