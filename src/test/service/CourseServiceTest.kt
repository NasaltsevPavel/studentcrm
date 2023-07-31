package studentcrm.service

import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import studentcrm.exceptions.NotFoundException
import studentcrm.model.course.CourseDTO
import studentcrm.model.course.CourseEntity
import studentcrm.model.student.StudentEntity
import studentcrm.model.type.CourseName
import studentcrm.model.type.toCourseName
import studentcrm.repository.CourseRepository
import java.util.*
import kotlin.collections.ArrayList

class CourseServiceTest {

    private val courseRepositoryMock = mockk<CourseRepository>()
    private val course1 = CourseEntity(1L, "Mathe", mutableListOf())
    private val course2 = CourseEntity(2L, "Bio", mutableListOf())
    private val course3 = CourseDTO(1L, CourseName("BWL"), mutableListOf())
    private val courseService = CourseService(courseRepositoryMock)

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @Test
    fun `should find all courses`() {

        val coursesEntity = mutableListOf(course1, course2)
        val courses: MutableList<CourseDTO> = arrayListOf()

        every { courseRepositoryMock.findAll() } returns coursesEntity

        coursesEntity.forEach { entity -> courses.add(fromEntity(entity)) }

        val coursesList = courseService.findAllCourses()

        Assertions.assertEquals(courses, coursesList)

        verify { courseRepositoryMock.findAll() }


    }

    @Test
    fun `should find course by id`() {


        every { courseRepositoryMock.findById(course1.courseID) } returns Optional.of(course1)
        every { courseRepositoryMock.findByCourseID(course1.courseID) } returns course1

        val course = courseService.findById(course1.courseID)

        Assertions.assertEquals(fromEntity(course1), course)

        verify { courseRepositoryMock.findByCourseID(course1.courseID) }


    }


    @Test
    fun `should get exception by finding course by id`() {

        every { courseRepositoryMock.findById(20L) } returns Optional.empty()

        assertThrows<NotFoundException> {
            courseService.findById(20L)
        }

        verify(exactly = 1) { courseRepositoryMock.findById(20L) }

    }

    @Test
    fun `should get find course by name`() {

        every { courseRepositoryMock.findByCourseName(course1.courseName) } returns Optional.of(course1)

        val course = courseService.findByName(course1.courseName)

        Assertions.assertEquals(course, Optional.of(fromEntity(course1)))

        verify { courseRepositoryMock.findByCourseName(course1.courseName) }

    }


    @Test
    fun `should get exception by finding course by name`() {

        every { courseRepositoryMock.findByCourseName("Name") } returns Optional.empty()

        assertThrows<NotFoundException> {
            courseService.findByName("Name")
        }

        verify(exactly = 1) { courseRepositoryMock.findByCourseName("Name") }

    }


    @Test
    fun `should create a course`() {

        every { courseRepositoryMock.save(any()) } returns toEntity(course3)
        val course = courseService.createCourse(course3)

        Assertions.assertNotNull(course)
        Assertions.assertEquals(course.courseID, course3.courseID)

    }

    @Test
    fun `should update course`() {

        every { courseRepositoryMock.existsById(course1.courseID) } returns true
        every { courseRepositoryMock.findById(course1.courseID) } returns Optional.of(course1)
        every { courseRepositoryMock.save(any()) } returns toEntity(course3)
        val course = courseService.updateCourse(course3)

        Assertions.assertEquals(course, course3)


    }

    @Test
    fun `should get exception by updating course`() {
        every { courseRepositoryMock.findById(course3.courseID) } returns Optional.empty()

        assertThrows<NotFoundException> {
            courseService.updateCourse(course3)
        }

    }

    @Test
    fun `should delete student`() {

        every { courseRepositoryMock.deleteById(course1.courseID) } just runs

        courseService.deleteCourseById(course1.courseID)

        verify(exactly = 1) { courseRepositoryMock.deleteById(course1.courseID) }
    }



    private fun fromEntity(entity: CourseEntity): CourseDTO {
        val studentsNames: ArrayList<String> = arrayListOf()

        entity.students.forEach { studentEntity -> studentsNames.add(studentEntity.lastName) }

        return CourseDTO(entity.courseID, entity.courseName.toCourseName(), studentsNames)

    }

    private fun toEntity(dto: CourseDTO): CourseEntity {
        val students: MutableList<StudentEntity> = mutableListOf()
        return CourseEntity(dto.courseID, dto.courseName.value, students)
    }

}