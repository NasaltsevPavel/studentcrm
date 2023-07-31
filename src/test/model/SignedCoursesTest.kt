package studentcrm.model

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import studentcrm.model.course.CourseEntity
import studentcrm.model.student.StudentEntity

class SignedCoursesTest {

    @Test
    fun `should remove Course properly`(){
        val courseMathe: CourseEntity = CourseEntity(1L,"Mathe", mutableListOf())
        val studentEntity: StudentEntity = StudentEntity(1L,"Pavel", "Nasaltsev","nasaltsev@web.de",
            mutableListOf(courseMathe))

        studentEntity.deleteCourse(courseMathe)
        assertFalse(studentEntity.signedCourses.contains(courseMathe))

    }

    @Test
    fun `should add Course properly`(){
        val courseMathe: CourseEntity = CourseEntity(1L,"Mathe", mutableListOf())
        val studentEntity: StudentEntity = StudentEntity(1L,"Pavel", "Nasaltsev","nasaltsev@web.de",
            mutableListOf())

        studentEntity.signedCourses.add(courseMathe)
        assertTrue(studentEntity.signedCourses.contains(courseMathe))
    }
}