package ORM.mapper;

import ORM.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper  // 让 Spring 扫描到
public interface StudentMapper {
    Student selectById(@Param("id") Long id);
    List<Student> selectAll();
    int insert(Student student);
    int update(Student student);
    int deleteById(@Param("id") Long id);

    // 分页查询数据
    List<Student> selectByPage(@Param("name") String name,
                               @Param("offset") int offset,
                               @Param("size") int size);

    // 查询总记录数
    long countByName(@Param("name") String name);
}
