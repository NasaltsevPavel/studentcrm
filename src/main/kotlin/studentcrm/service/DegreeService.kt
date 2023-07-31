package studentcrm.service

import org.springframework.stereotype.Service
import studentcrm.exceptions.InvalidInputException
import studentcrm.exceptions.NotFoundException
import studentcrm.model.course.CourseEntity
import studentcrm.model.degree.DegreeDTO
import studentcrm.model.degree.DegreeEntity
import studentcrm.model.student.StudentEntity
import studentcrm.model.type.Action
import studentcrm.model.type.toDegreeName
import studentcrm.repository.CourseRepository
import studentcrm.repository.DegreeRepository
import studentcrm.repository.StudentRepository
import kotlin.jvm.optionals.getOrNull

@OptIn(ExperimentalStdlibApi::class)
@Service
class DegreeService(
    private val degreeRepository: DegreeRepository,
    private val studentRepository: StudentRepository,
    private val courseRepository: CourseRepository
) {

    fun createDegree(degree: DegreeDTO) = fromEntity(degreeRepository.save(toEntity(degree)))

    fun findAllDegrees(): List<DegreeDTO> {
        val degrees: MutableList<DegreeDTO> = arrayListOf()

        degreeRepository.findAll().let { it.forEach { entity -> degrees.add(fromEntity(entity)) } }

        return degrees
    }

    fun addOrRemoveStudent(studentId: Long, degreeId: Long, action: Action) {
        studentRepository.findById(studentId).getOrNull()?.let { student ->
            degreeRepository.findById(degreeId).getOrNull()?.let { degree ->
                if (action.name == "REMOVE") {
                    degree.degreeStudents.remove(student)
                    degreeRepository.save(degree)
                } else {
                    degree.degreeStudents.add(student)
                    degreeRepository.save(degree)
                }
            } ?: throw NotFoundException("Degree with ID: $degreeId not found")
        } ?: throw NotFoundException("Student with ID: $studentId not found")
    }

    fun addOrRemoveCourse(courseId: Long, degreeId: Long, action: Action) {
        courseRepository.findById(courseId).getOrNull()?.let { course ->
            degreeRepository.findById(degreeId).getOrNull()?.let { degree ->
                if (action.name == "REMOVE") {
                    degree.degreeCourses.remove(course)
                    degreeRepository.save(degree)
                } else {
                    degree.addCourse(course)
                    degreeRepository.save(degree)
                }
            } ?: throw NotFoundException("Degree with ID: $degreeId not found")
        } ?: throw NotFoundException("Course with ID: $courseId not found")
    }

    private fun fromEntity(entity: DegreeEntity): DegreeDTO {
        val students: ArrayList<String> = arrayListOf()
        val courses: ArrayList<String> = arrayListOf()

        entity.degreeStudents.forEach { students.add(it.lastName) }
        entity.degreeCourses.forEach { courses.add(it.courseName) }

        return DegreeDTO(entity.degreeID, entity.degreeName.toDegreeName(), students, courses)
    }

    private fun toEntity(dto: DegreeDTO): DegreeEntity {
        val students: MutableList<StudentEntity> = mutableListOf()
        val courses: MutableList<CourseEntity> = mutableListOf()

        return DegreeEntity(dto.degreeID, dto.degreeName.value, students, courses)
    }
}