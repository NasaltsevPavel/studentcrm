package studentcrm.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import studentcrm.model.course.CourseDTO
import studentcrm.model.type.CourseName

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {
    val baseUrl = "/course-api/"

    @Nested
    @DisplayName("GET /course-api/courses")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCourses {

        @Test
        fun `should return all courses`() {

            mockMvc.get("$baseUrl/courses")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("[0].courseID") { value(1L) }
                    jsonPath("[1].courseID") { value(2L) }
                }
        }
    }


    @Nested
    @DisplayName("GET /course-api/course/{courseID}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCourse {

        @Test
        fun `should return the course with the given id`() {

            val id = 1L

            mockMvc.get("$baseUrl/course/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.courseName") { value("Mathe") }
                }
        }

        @Test
        fun `should return NOT FOUND if the id does not exist`() {

            val id = 1000L

            mockMvc.get("$baseUrl/course/$id")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }


    @Nested
    @DisplayName("GET /course-api/course/name/{name}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCourseByName {

        @Test
        fun `should return the course with the given name`() {

            val name = "Mathe"

            mockMvc.get("$baseUrl/course/name/$name")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.courseID") { value(1L) }
                    jsonPath("$.courseName") { value("Mathe") }
                }
        }

        @Test
        fun `should return NOT FOUND if the name does not exist`() {

            val name = "Name"

            mockMvc.get("$baseUrl/course/name/$name")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }


    @Nested
    @DisplayName("POST  /course-api/course/create")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewCourse {

        @Test
        fun `should add the new course`() {

            val newCourse = CourseDTO(99L, CourseName("Test"), mutableListOf())

            val performPost = mockMvc.post("$baseUrl/course/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newCourse)
            }

            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                }

        }

        @Test
        fun `should return status Bad Request, message`() {

            val newCourse = CourseDTO(100L, CourseName("T"), mutableListOf())


            mockMvc.post("$baseUrl/course/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newCourse)
            }

                .andExpect { status { isNotAcceptable() } }
        }


    }





}