package studentcrm.service

import org.springframework.stereotype.Service
import studentcrm.exceptions.InvalidInputException
import studentcrm.exceptions.NotFoundException
import studentcrm.model.course.CourseEntity
import studentcrm.model.grade.GradeDTO
import studentcrm.model.grade.GradeEntity
import studentcrm.model.student.StudentEntity
import studentcrm.repository.CourseRepository
import studentcrm.repository.GradeRepository
import studentcrm.repository.StudentRepository
import kotlin.jvm.optionals.getOrNull

@OptIn(ExperimentalStdlibApi::class)
@Service
class GradeService(
    private val gradeRepository: GradeRepository,
    private val courseRepository: CourseRepository,
    private val studentRepository: StudentRepository
) {

    fun createGrade(grade: GradeDTO) =
        studentRepository.findByLastName(grade.student).getOrNull()?.let {
            courseRepository.findByCourseName(grade.course).getOrNull()?.let {
                fromEntity(gradeRepository.save(toEntity(grade)))
            } ?: throw NotFoundException("Course with name: ${grade.course} not found")
        } ?: throw NotFoundException("Student with name: ${grade.student} not found")

    fun deleteGrade(gradeId: Long) = gradeRepository.deleteById(gradeId)

    fun findAllGrades(): List<GradeDTO> {

        val grades: MutableList<GradeDTO> = arrayListOf()
        gradeRepository.findAll().let { it.forEach { entity -> grades.add(fromEntity(entity)) } }

        return grades
    }

    fun fromEntity(entity: GradeEntity) =
        GradeDTO(entity.gradeId, entity.studentGrade.lastName, entity.courseGrade.courseName, entity.grade, "${entity.courseGrade.courseName}: ${entity.grade}")

    private fun toEntity(dto: GradeDTO): GradeEntity{

        val courses: MutableList<String> = mutableListOf()
        studentRepository.findByLastName(dto.student).get().grades.forEach { courses.add(it.courseGrade.courseName) }

        if (courses.contains(dto.course)){
            throw InvalidInputException("Grade is already added to this Course")
        }

        return   GradeEntity(
            dto.gradeId, studentRepository.findByLastName(dto.student).get(),
            courseRepository.findByCourseName(dto.course).get(), dto.grade
        )
    }


}