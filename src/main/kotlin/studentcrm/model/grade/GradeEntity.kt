package studentcrm.model.grade

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Range
import studentcrm.model.course.CourseEntity
import studentcrm.model.student.StudentEntity

@Entity
@Table(name = "grades")
class GradeEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "gradeId")
    val gradeId: Long,

    @ManyToOne
    @JoinTable(name="grade_student",
        joinColumns = [JoinColumn(name = "gradeId")],
        inverseJoinColumns = [JoinColumn(name="studentId")])
    var studentGrade: StudentEntity,

    @ManyToOne
    @JoinTable(name="grade_course",
        joinColumns = [JoinColumn(name = "gradeId")],
        inverseJoinColumns = [JoinColumn(name="courseId")])
    var courseGrade: CourseEntity,

    @NotBlank
    @Range(min=1, max=5)
    @Column(name = "grade")
    var grade: Double
)