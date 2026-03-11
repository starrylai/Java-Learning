package ORM.OneToManyExample.JPA;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@IdClass(EnrollmentId.class)
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
