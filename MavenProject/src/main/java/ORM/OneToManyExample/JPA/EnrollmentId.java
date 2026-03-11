package ORM.OneToManyExample.JPA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentId implements Serializable {
    private Long student;
    private Long course;
}
