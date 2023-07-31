package studentcrm.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import studentcrm.model.degree.DegreeDTO
import studentcrm.model.type.Action
import studentcrm.service.DegreeService

@RestController
@RequestMapping("/degree-api")
class DegreeController(val degreeService: DegreeService) {

    @GetMapping("/degrees")
    @ResponseStatus(HttpStatus.OK)
    fun findAll() = degreeService.findAllDegrees()

    @PostMapping("/degree")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCourse(@Valid @RequestBody degree: DegreeDTO) = degreeService.createDegree(degree)

   @PutMapping("/{action}/student/{studentID}/degree/{degreeID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun addStudentToDegree(@PathVariable studentID: Long, @PathVariable degreeID: Long, @PathVariable action: Action) =
        degreeService.addOrRemoveStudent(studentID,degreeID,action)

    @PutMapping("/{action}/course/{courseID}/degree/{degreeID}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun addCourseToDegree(@PathVariable courseID: Long, @PathVariable degreeID: Long, @PathVariable action: Action) =
        degreeService.addOrRemoveCourse(courseID,degreeID,action)

}