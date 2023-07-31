package studentcrm.model.degree

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import studentcrm.model.course.CourseEntity
import studentcrm.model.student.StudentEntity

@Entity
@Table(name = "degree")
class DegreeEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "degreeId")
    var degreeID: Long,

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(name = "degreeName")
    var degreeName: String,

    @OneToMany(mappedBy = "degree")
    var degreeStudents: MutableList<StudentEntity>,

    @ManyToMany
    @JoinTable(name="degree_course",
        joinColumns = [JoinColumn(name = "degreeId")],
        inverseJoinColumns = [JoinColumn(name="courseId")])
    val degreeCourses: MutableList<CourseEntity>
){
    fun addCourse(courseEntity: CourseEntity){
        degreeCourses.add(courseEntity)
    }

    fun deleteStudent(studentEntity: StudentEntity) {

        if (degreeStudents.contains(studentEntity)) {
            degreeStudents.remove(studentEntity)
        }

    }

}