package ORM;

import ORM.entity.StudentJPA;
import ORM.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JpaMain implements CommandLineRunner {
    @Autowired
    private StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(JpaMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 创建
        StudentJPA s = new StudentJPA("李四", 22, "lisi@example.com");
        s = studentService.createStudent(s);
        System.out.println("插入成功，ID=" + s.getId());

        // 查询
        StudentJPA found = studentService.findById(s.getId());
        System.out.println("查询结果：" + found.getName());

        // 更新
        found.setAge(23);
        studentService.updateStudent(found);

        // 查询所有
        studentService.findAll().forEach(stu -> System.out.println(stu.getName()));

        // 派生查询
        studentService.searchByName("李").forEach(stu -> System.out.println("模糊匹配：" + stu.getName()));

        // 删除
        studentService.deleteStudent(s.getId());

        try {
            StudentJPA exs = new StudentJPA("事务测试", 20, "test@example.com");
            studentService.createWithRollback(exs);
        } catch (Exception e) {
            System.out.println("捕获异常：" + e.getMessage());
        }
        // 验证数据库中是否有新增数据
        List<StudentJPA> all = studentService.findAll();
        System.out.println("当前总记录数：" + all.size());
    }
}
