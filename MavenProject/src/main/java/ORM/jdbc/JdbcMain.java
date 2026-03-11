package ORM.jdbc;

import ORM.entity.Student;

import java.sql.SQLException;

public class JdbcMain {
    public static void main(String[] args) throws SQLException {
        StudentDao dao = new StudentDao(DataSourceUtil.getDataSource());

        // 插入
        Student s = new Student("张三", 20, "zhangsan@example.com");
        dao.insert(s);
        System.out.println("插入成功，ID=" + s.getId());

        // 查询
        Student found = dao.findById(s.getId());
        System.out.println("查询结果：" + found.getName());

        // 更新
        found.setAge(21);
        dao.update(found);

        // 查询所有
        dao.findAll().forEach(stu -> System.out.println(stu.getName()));

        // 删除
        dao.delete(s.getId());
    }
}
