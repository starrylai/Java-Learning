package ORM.OneToManyExample.Mybatis;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Student {
    private int id;
    private String name;
    private Integer age;
    private String email;
    private List<Enrollment> enrollments;
}
