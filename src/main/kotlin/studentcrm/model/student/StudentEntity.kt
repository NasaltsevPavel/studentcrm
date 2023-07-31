package studentcrm.model.student

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import studentcrm.model.course.CourseEntity
import studentcrm.model.degree.DegreeEntity
import studentcrm.model.grade.GradeEntity

@Entity
@Table(name = "students")
class StudentEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "studentId")
    var studentID: Long,

    @NotBlank
    @Size(min = 2, max = 20)
    @Column(name = "firstName")
    var firstName: String,

    @NotBlank
    @Size(min = 2, max = 20)
    @Column(name = "lastName")
    var lastName: String,

    @NotBlank
    @Email
    @Column(unique = true, nullable = false, name = "email")
    var email: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_course",
        joinColumns = [JoinColumn(name = "studentId")],
        inverseJoinColumns = [JoinColumn(name = "courseId")]
    )
    var signedCourses: MutableList<CourseEntity>,

    @ManyToOne
    @JoinTable(
        name = "degree_student",
        joinColumns = [JoinColumn(name = "studentId")],
        inverseJoinColumns = [JoinColumn(name = "degreeId")]
    )
    var degree: DegreeEntity,

    @OneToMany(mappedBy = "studentGrade")
    var grades: List<GradeEntity>,

    @Column(name = "averageGrade")
    var averageGrade: Double,

    ) {

    fun deleteCourse(courseEntity: CourseEntity) {

        if (signedCourses.contains(courseEntity)) {
            signedCourses.remove(courseEntity)
        }

    }

}