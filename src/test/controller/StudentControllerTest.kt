package studentcrm.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*
import studentcrm.model.student.StudentDTO
import studentcrm.model.type.FirstName
import studentcrm.model.type.LastName
import studentcrm.service.CourseService
import studentcrm.service.StudentService

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val studentService: StudentService,
    val courseService: CourseService
) {

    val baseUrl = "/student-api/"

    @Nested
    @DisplayName("GET /student-api/students")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStudents {

        @Test
        fun `should return all students`() {

            mockMvc.get("$baseUrl/students")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("[0].studentID") { value(1L) }
                    jsonPath("[1].studentID") { value(2L) }
                }
        }
    }

    @Nested
    @DisplayName("GET /student-api/student/{studentId}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStudent {

        @Test
        fun `should return the student with the given id`() {

            val id = 2L

            mockMvc.get("$baseUrl/student/$id")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.lastname") { value("Weber") }
                    jsonPath("$.firstname") { value("Anna") }
                }
        }

        @Test
        fun `should return NOT FOUND if the id does not exist`() {

            val id = 1000L

            mockMvc.get("$baseUrl/student/$id")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("GET /student-api/student/name/{lastname}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStudentByName {

        @Test
        fun `should return the student with the given lastname`() {

            val lastname = "Nasaltsev"

            mockMvc.get("$baseUrl/student/name/$lastname")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.studentID") { value(1L) }
                    jsonPath("$.firstname") { value("Pavel") }
                }
        }

        @Test
        fun `should return NOT FOUND if the lastname does not exist`() {

            val lastname = "Lastname"

            mockMvc.get("$baseUrl/student/name/$lastname")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /student-api/student/create")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewStudent {

        @Test
        fun `should add the new student`() {

            val newStudent = StudentDTO(99L, FirstName("Test"), LastName("Test"), "test@gmail.com", mutableListOf())


            val performPost = mockMvc.post("$baseUrl/student/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newStudent)
            }


            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                }

        }

        @Test
        fun `should return status Bad Request, message`() {

            val newStudent = StudentDTO(99L, FirstName("Test"), LastName("Test"), "testgmail.com", mutableListOf())


            mockMvc.post("$baseUrl/student/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newStudent)
            }

                .andExpect { status { isNotAcceptable() } }
        }


    }

    @Nested
    @DisplayName("PUT /student-api/student/update")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateExistingStudent {

        @Test
        fun `should update an existing student`() {

            val updatedStudent = StudentDTO(52L, FirstName("Olga"), LastName("Kumm"), "kumm@gmail.com", mutableListOf())


            val performPatchRequest = mockMvc.put("$baseUrl/student/update") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedStudent)
            }


            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedStudent))
                    }
                }

            mockMvc.get("$baseUrl/student/${updatedStudent.studentID}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedStudent)) } }
        }

        @Test
        fun `should return BAD REQUEST if no student with given id exists`() {

            val invalidStudent = StudentDTO(9999L, FirstName("Olga"), LastName("Kumm"), "kumm@gmail.com", mutableListOf())


            val performPatchRequest = mockMvc.put(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidStudent)
            }


            performPatchRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE /student-api/student/delete/{studentId}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingStudent {

        @Test
        @DirtiesContext
        fun `should delete the student with the given id`() {

            val id = 3L


            mockMvc.delete("$baseUrl/student/delete/$id")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("$baseUrl/student/$id")
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("PUT /student-api/add/student/{studentId}/course/{courseId}")
    @Transactional
    inner class EnrollStudent {
        @Test
        fun `Test for enrolling a student into a course`() {

            val studentId = studentService.findAllStudents().first().studentID
            val courseId = courseService.findAllCourses().first().courseID

            mockMvc.put("/student-api/add/student/$studentId/course/$courseId")
                .andExpect {
                    status { isAccepted() }
                }
                .andDo { print() }
        }
    }

    @Nested
    @DisplayName("PUT /student-api/remove/student/{studentId}/course/{courseId}")
    @Transactional
    inner class DisenrollStudent {
        @Test
        fun `Test for disenrolling a student from a course`() {

            val studentId = studentService.findAllStudents().first().studentID
            val courseId = courseService.findAllCourses().first().courseID

            mockMvc.put("/student-api/remove/student/$studentId/course/$courseId")
                .andExpect {
                    status { isAccepted() }
                }
                .andDo { print() }
        }
    }




}





