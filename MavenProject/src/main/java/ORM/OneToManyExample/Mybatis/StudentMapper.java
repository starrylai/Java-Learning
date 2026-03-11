package ORM.OneToManyExample.Mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> selectAllWithCourses();
    Student selectByIdWithCourses(@Param("id") int id);
}