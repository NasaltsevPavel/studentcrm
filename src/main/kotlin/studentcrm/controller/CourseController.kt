package studentcrm.controller

import jakarta.annotation.security.RolesAllowed
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import studentcrm.model.course.CourseDTO
import studentcrm.service.CourseService

@RestController
@RequestMapping("/course-api")
class CourseController(val courseService: CourseService) {

    @GetMapping("/courses")
    @ResponseStatus(HttpStatus.OK)
    fun findAll() = courseService.findAllCourses()

    @PostMapping("/course")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCourse(@Valid @RequestBody course: CourseDTO) = courseService.createCourse(course)

    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(@PathVariable id: Long) = courseService.findById(id)

    @GetMapping("/course/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    fun findByName(@PathVariable name: String) = courseService.findByName(name)

    @PutMapping("/course")
    @ResponseStatus(HttpStatus.OK)
    fun updateCourse(@Valid @RequestBody course: CourseDTO) = courseService.updateCourse(course)

    @DeleteMapping("/course/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable id: Long) = courseService.deleteCourseById(id)

    @GetMapping("/course/{courseID}/grade/average")
    @ResponseStatus(HttpStatus.OK)
    fun calcAverageGradeCourse(@PathVariable courseID: Long) = courseService.calcAverageGrade(courseID)

}