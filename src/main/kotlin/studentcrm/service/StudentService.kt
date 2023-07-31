package studentcrm.service

import org.springframework.stereotype.Service
import studentcrm.exceptions.InvalidInputException
import studentcrm.exceptions.NotFoundException
import studentcrm.model.course.CourseEntity
import studentcrm.model.grade.GradeEntity
import studentcrm.model.student.StudentDTO
import studentcrm.model.student.StudentEntity
import studentcrm.model.type.Action
import studentcrm.model.type.toFirstName
import studentcrm.model.type.toLastName
import studentcrm.repository.CourseRepository
import studentcrm.repository.DegreeRepository
import studentcrm.repository.StudentRepository
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.optionals.getOrNull

@OptIn(ExperimentalStdlibApi::class)
@Service
class StudentService(
    private val studentRepository: StudentRepository,
    private val courseRepository: CourseRepository,
    private val degreeRepository: DegreeRepository,
    private val gradeService: GradeService
) {
    fun createStudent(student: StudentDTO) = fromEntity(studentRepository.save(toEntity(student)))

    fun findAllStudents(): List<StudentDTO> {
        val students: MutableList<StudentDTO> = arrayListOf()
        studentRepository.findAll().let { studentEntities ->
            studentEntities.forEach { students.add(fromEntity(it)) }
        }
        return students
    }

    fun findById(id: Long) =
        studentRepository.findById(id).getOrNull()?.let { fromEntity(it) }
            ?: throw NotFoundException("Student with ID: $id not found")

    fun findByLastName(lastname: String) =
        studentRepository.findByLastName(lastname).getOrNull()?.let { fromEntity(it) }
            ?: throw NotFoundException("Student with name: $lastname not found")

    fun updateStudent(student: StudentDTO) =
        studentRepository.findById(student.studentID).getOrNull()?.let { studentEntity ->
            studentEntity.firstName = student.firstname.value
            studentEntity.lastName = student.lastname.value
            studentEntity.email = student.email
            fromEntity(studentRepository.save(studentEntity))
        } ?: throw NotFoundException("Student with ID: ${student.studentID} not found")

    fun deleteStudentByID(id: Long) = studentRepository.deleteById(id)

    fun addOrRemoveStudentFromCourse(action: Action, studentID: Long, courseID: Long) {
        studentRepository.findById(studentID).getOrNull()?.let { student ->
            courseRepository.findById(courseID).getOrNull()?.let { course ->
                if (action.name == "REMOVE") {
                    student.deleteCourse(course)
                    studentRepository.save(student)
                } else {
                    if (student.signedCourses.contains(course)) {
                        throw InvalidInputException("Course is already added")
                    } else {
                        student.signedCourses.add(course)
                        studentRepository.save(student)
                    }
                }
            } ?: throw NotFoundException("Course with ID: $courseID not found")
        } ?: throw NotFoundException("Student with ID: $studentID not found")
    }

    fun calcAverageGrade(studentID: Long) {
         studentRepository.findById(studentID).getOrNull()?.let {
            var grades = 0.0
            it.grades.forEach { gradeEntity -> grades += gradeEntity.grade }
            it.averageGrade = grades / it.grades.size
            studentRepository.save(it)

        } ?: throw NotFoundException("Student with ID: $studentID not found")
    }

    private fun fromEntity(entity: StudentEntity): StudentDTO {
        calcAverageGrade(entity.studentID)
        val courseNames: MutableMap<String, String> = mutableMapOf()
        val grades: MutableMap<String, String> = mutableMapOf()

        entity.let {
            it.signedCourses.forEach { courseEntity ->
                courseNames[courseEntity.courseName] =
                    "${courseEntity.room.locationRoom.locationName}: ${courseEntity.room.roomNumber} "
            }
            it.grades.forEach { gradeEntity ->
                if (gradeEntity.grade == 5.0) {
                    grades[gradeService.fromEntity(gradeEntity).courseGrade!!] = "not passed"
                } else {
                    grades[gradeService.fromEntity(gradeEntity).courseGrade!!] = "passed"
                }
            }
            return StudentDTO(
                entity.studentID, entity.firstName.toFirstName(), entity.lastName.toLastName(),
                entity.email, entity.degree.degreeName, courseNames, grades, entity.averageGrade
            )
        }
    }

    private fun toEntity(dto: StudentDTO): StudentEntity {
        val courseE: MutableList<CourseEntity> = mutableListOf()
        val grades: List<GradeEntity> = listOf()

        return StudentEntity(
            dto.studentID, dto.firstname.value, dto.lastname.value, dto.email,
            courseE, degreeRepository.findByDegreeName(dto.degree).get(), grades, 0.0
        )
    }
}