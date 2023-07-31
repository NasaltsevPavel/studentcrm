package studentcrm.controller

import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import studentcrm.model.student.StudentDTO
import studentcrm.model.type.Action
import studentcrm.service.StudentService

@RestController
@RequestMapping("/student-api")
class StudentController(val studentService: StudentService) {

    @GetMapping("/students")
    @ResponseStatus(HttpStatus.OK)
    fun findAll() = studentService.findAllStudents()

    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable id: Long) = studentService.findById(id)

    @GetMapping("/student/lastname/{lastname}")
    @ResponseStatus(HttpStatus.OK)
    fun findByLastName(@PathVariable lastname: String) = studentService.findByLastName(lastname)

    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    fun createStudent(@Valid @RequestBody student: StudentDTO) = studentService.createStudent(student)

    @PutMapping("/student")
    @ResponseStatus(HttpStatus.OK)
    fun updateStudent(@Valid @RequestBody student: StudentDTO) = studentService.updateStudent(student)

    @DeleteMapping("/student/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStudent(@PathVariable id: Long) = studentService.deleteStudentByID(id)

    @PutMapping("/{action}/student/{studentID}/course/{courseID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun addStudentToCourse(@PathVariable studentID: Long, @PathVariable courseID: Long, @PathVariable action: Action) =
        studentService.addOrRemoveStudentFromCourse(action,studentID, courseID)

  //  @GetMapping("/student/{studentID}/grade/average")
  //  @ResponseStatus(HttpStatus.OK)
  //  fun calcAverageGradeStudent(@PathVariable studentID: Long) = studentService.calcAverageGrade(studentID)

}