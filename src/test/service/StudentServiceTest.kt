package studentcrm.service

import io.mockk.*
import studentcrm.repository.StudentRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import studentcrm.exceptions.NotFoundException
import studentcrm.model.course.CourseEntity
import studentcrm.model.student.StudentDTO
import studentcrm.model.student.StudentEntity
import studentcrm.model.type.FirstName
import studentcrm.model.type.LastName
import studentcrm.model.type.toFirstName
import studentcrm.model.type.toLastName
import studentcrm.repository.CourseRepository
import java.util.Optional

class StudentServiceTest {

    private val studentRepositoryMock = mockk<StudentRepository>()
    private val courseRepositoryMock = mockk<CourseRepository>()

    private val studentService = StudentService(studentRepositoryMock, courseRepositoryMock)

    private val course1 = CourseEntity(1L, "Mathe", mutableListOf())

    private var student1 = StudentEntity(
        1L,
        "Gabriel",
        "Kumm",
        "mueGab@hmail.com",
        mutableListOf(course1)
    )
    private var student2 = StudentEntity(2L, "Lina", "Fischer", "linafi@hmail.com", mutableListOf())
    private var student3 = StudentEntity(
        3L,
        "Pavel",
        "Nasaltsev",
        "nasaltsev@web.de",
        mutableListOf(CourseEntity(1L, "Mathe", mutableListOf()))
    )
    private var student4 = StudentDTO(2L, FirstName("Anna"), LastName("Fischer"), "linafi@hmail.com", mutableListOf())

    private val student5 = StudentEntity(
        1L,
        "Gabriel",
        "Kumm",
        "mueGab@hmail.com",
        mutableListOf()
    )

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should find all students`() {
        val studentsEntity = mutableListOf(student1, student2, student3)
        val students: MutableList<StudentDTO> = arrayListOf()

        every { studentRepositoryMock.findAll() } returns studentsEntity

        studentsEntity.forEach { entity -> students.add(fromEntity(entity)) }

        val studentList = studentService.findAllStudents()

        assertEquals(students, studentList)

        verify { studentRepositoryMock.findAll() }


    }

    @Test
    fun `should find student by id`() {


        every { studentRepositoryMock.existsById(student1.studentID) } returns true
        every { studentRepositoryMock.findByStudentID(student1.studentID) } returns student1
        every { studentRepositoryMock.findById(student1.studentID).isEmpty } returns false

        val student = studentService.findById(student1.studentID)


        assertEquals(fromEntity(student1), student)

        verify { studentRepositoryMock.findByStudentID(student1.studentID) }


    }

    @Test
    fun `should get exception by finding student by id`() {

        every { studentRepositoryMock.findById(20L) } returns Optional.empty()

        assertThrows<NotFoundException> {
            studentService.findById(20L)
        }

        verify(exactly = 1) { studentRepositoryMock.findById(20L) }

    }

    @Test
    fun `should get find student by lastname`() {

        every { studentRepositoryMock.findByLastName(student1.lastName) } returns Optional.of(student1)

        val student = studentService.findByLastName(student1.lastName)

        assertEquals(student, Optional.of(fromEntity(student1)))

        verify { studentRepositoryMock.findByLastName(student1.lastName) }

    }


    @Test
    fun `should get exception by finding student by lastname`() {

        every { studentRepositoryMock.findByLastName("Lastname") } returns Optional.empty()

        assertThrows<NotFoundException> {
            studentService.findByLastName("Lastname")
        }

        verify(exactly = 1) { studentRepositoryMock.findByLastName("Lastname") }

    }


    @Test
    fun `should create a student`() {

        every { studentRepositoryMock.save(any()) } returns toEntity(student4)
        val student = studentService.createStudent(student4)

        assertNotNull(student)
        assertEquals(student.studentID, student4.studentID)

    }

    @Test
    fun `should update student`() {

        every { studentRepositoryMock.existsById(student2.studentID) } returns true
        every { studentRepositoryMock.findById(student2.studentID) } returns Optional.of(student2)
        every { studentRepositoryMock.save(any()) } returns toEntity(student4)
        val student = studentService.updateStudent(student4)

        assertEquals(student, student4)


    }

    @Test
    fun `should get exception by updating student`() {
        every { studentRepositoryMock.findById(student4.studentID) } returns Optional.empty()

        assertThrows<NotFoundException> {
            studentService.updateStudent(student4)
        }

    }

    @Test
    fun `should delete student`() {

        every { studentRepositoryMock.deleteById(student1.studentID) } just runs

        studentService.deleteStudentByID(student1.studentID)

        verify(exactly = 1) { studentRepositoryMock.deleteById(student1.studentID) }
    }


    @Test
    fun `should add student to course`() {

        every { studentRepositoryMock.findById(student5.studentID) } returns Optional.of(student5)
        every { courseRepositoryMock.findById(course1.courseID) } returns Optional.of(course1)
        every { studentRepositoryMock.save(any()) } returns student1

        studentService.addStudentToCourse(student5.studentID, course1.courseID)

        assertEquals(student5.signedCourses[0].courseName, student1.signedCourses[0].courseName)

    }

    @Test
    fun `should get exception by adding student to course`() {
        every { studentRepositoryMock.findById(student5.studentID) } returns Optional.empty()

        assertThrows<NotFoundException> {
            studentService.addStudentToCourse(student5.studentID, course1.courseID)
        }

    }

    @Test
    fun `should remove student from course`() {


        every { studentRepositoryMock.findById(student1.studentID) } returns Optional.of(student1)
        every { courseRepositoryMock.findById(course1.courseID) } returns Optional.of(course1)
        every { studentRepositoryMock.save(any()) } returns student5

        studentService.deleteStudentFromCourse(student1.studentID, course1.courseID)

        assertEquals(student1.signedCourses.size, student5.signedCourses.size)

    }

    @Test
    fun `should get exception by removing student from course`() {

        every { studentRepositoryMock.findById(student5.studentID) } returns Optional.empty()

        assertThrows<NotFoundException> {
            studentService.deleteStudentFromCourse(student5.studentID, course1.courseID)
        }
    }


    private fun fromEntity(entity: StudentEntity): StudentDTO {
        val courseNames: ArrayList<String> = arrayListOf()

        entity.signedCourses.forEach { courseEntity -> courseNames.add(courseEntity.courseName) }

        return StudentDTO(entity.studentID, entity.firstName.toFirstName(), entity.lastName.toLastName(), entity.email, courseNames)

    }

    private fun toEntity(dto: StudentDTO): StudentEntity {
        val courseE: MutableList<CourseEntity> = mutableListOf()
        return StudentEntity(dto.studentID, dto.firstname.value, dto.lastname.value, dto.email, courseE)
    }
}
