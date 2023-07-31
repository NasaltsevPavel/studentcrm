package studentcrm.model.course

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import studentcrm.model.degree.DegreeEntity
import studentcrm.model.grade.GradeEntity
import studentcrm.model.room.RoomEntity
import studentcrm.model.student.StudentEntity

@Entity
@Table(name = "courses")
class CourseEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "courseId")
    var courseID: Long,

    @NotBlank
    @Size(min=1, max = 35)
    @Column(name = "courseName")
    var courseName:String,

    @ManyToMany(mappedBy = "signedCourses")
    var students: MutableList<StudentEntity>,

    @Column(name = "averageGrade")
    var averageGrade: Double,

    @ManyToMany(mappedBy = "degreeCourses")
    var degrees: List<DegreeEntity>,

    @ManyToOne
    @JoinTable(name="course_room",
        joinColumns = [JoinColumn(name = "courseId")],
        inverseJoinColumns = [JoinColumn(name="roomId")])
    var room: RoomEntity,

    @OneToMany(mappedBy = "courseGrade")
    var grades: List<GradeEntity>


    )