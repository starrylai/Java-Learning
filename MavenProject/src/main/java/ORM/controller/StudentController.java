package ORM.controller;

import ORM.PageResult;
import ORM.entity.Student;
import ORM.entity.StudentJPA;
import ORM.service.HttpStudentService;
import ORM.service.StudentServiceMB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private HttpStudentService studentService;
//    private StudentServiceMB studentService;

    @GetMapping("/search")
    public PageResult<StudentJPA> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return studentService.searchByName(keyword == null ? "" : keyword, page, size);
    }

//    public PageResult<Student> search(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return studentService.searchByName(keyword == null ? "" : keyword, page, size);
//    }
}
