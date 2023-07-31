package studentcrm.service

import org.springframework.stereotype.Service
import studentcrm.exceptions.NotFoundException
import studentcrm.model.course.CourseDTO
import studentcrm.model.course.CourseEntity
import studentcrm.model.degree.DegreeEntity
import studentcrm.model.grade.GradeEntity
import studentcrm.model.student.StudentEntity
import studentcrm.model.type.toCourseName
import studentcrm.repository.CourseRepository
import studentcrm.repository.RoomRepository
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.optionals.getOrNull

@OptIn(ExperimentalStdlibApi::class)
@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val roomRepository: RoomRepository
) {

    fun createCourse(course: CourseDTO) = fromEntity(courseRepository.save(toEntity(course)))


    fun findAllCourses(): List<CourseDTO> {

        val courses: MutableList<CourseDTO> = arrayListOf()
        courseRepository.findAll().forEach { courses.add(fromEntity(it)) }

        return courses
    }

    fun findById(id: Long) =
        courseRepository.findById(id).getOrNull()?.let { fromEntity(courseRepository.findByCourseID(id)) }
            ?: throw NotFoundException("Course with ID: $id not found")

    fun findByName(name: String) =
        courseRepository.findByCourseName(name).getOrNull()?.let { fromEntity(it) }
            ?: throw NotFoundException("Course with name: $name not found")


    fun updateCourse(course: CourseDTO) =
        courseRepository.findById(course.courseID).getOrNull()?.let { courseEntity ->
            courseEntity.courseID = course.courseID
            courseEntity.courseName = course.courseName.value
             fromEntity(courseRepository.save(courseEntity))
        } ?: throw NotFoundException("Course with ID: ${course.courseID} not found")


    fun deleteCourseById(id: Long) = courseRepository.deleteById(id)

    fun calcAverageGrade(courseID: Long) {
        courseRepository.findById(courseID).getOrNull()?.let {
            var grades = 0.0

            it.grades.forEach { gradeEntity -> grades += gradeEntity.grade }

            it.averageGrade = grades / it.grades.size
            courseRepository.save(it)
        }?:throw NotFoundException("Course with ID: $courseID not found")

    }

    private fun toEntity(dto: CourseDTO): CourseEntity {
        val degrees: List<DegreeEntity> = mutableListOf()
        val grades: List<GradeEntity> = listOf()
        val studentsE: MutableList<StudentEntity> = mutableListOf()

        return CourseEntity(
            dto.courseID, dto.courseName.value, studentsE, 0.0, degrees,
            roomRepository.findByRoomNumber(dto.room).get(), grades
        )
    }

    private fun fromEntity(entity: CourseEntity): CourseDTO {
        val studentsNames: ArrayList<String> = arrayListOf()
        val degrees: ArrayList<String> = arrayListOf()
        val grades: ArrayList<Double> = arrayListOf()

        entity.degrees.forEach { degrees.add(it.degreeName) }
        entity.students.forEach { studentEntity -> studentsNames.add(studentEntity.lastName) }
        entity.grades.forEach { gradeEntity -> grades.add(gradeEntity.grade) }

        return CourseDTO(
            entity.courseID,
            entity.courseName.toCourseName(),
            degrees,
            entity.room.roomNumber,
            entity.room.locationRoom.locationName,
            entity.averageGrade,
            studentsNames,
            grades
        )
    }

}