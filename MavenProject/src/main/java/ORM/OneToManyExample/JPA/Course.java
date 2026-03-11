package ORM.OneToManyExample.JPA;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "course")
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
