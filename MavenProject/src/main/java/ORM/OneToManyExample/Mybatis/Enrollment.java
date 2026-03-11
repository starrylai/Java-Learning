package ORM.OneToManyExample.Mybatis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enrollment {
    private Student student;
    private Course course;
}
