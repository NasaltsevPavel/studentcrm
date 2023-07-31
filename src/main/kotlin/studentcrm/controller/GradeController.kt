package studentcrm.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import studentcrm.model.grade.GradeDTO
import studentcrm.service.GradeService

@RestController
@RequestMapping("/grade-api")
class GradeController(val gradeService: GradeService) {

    @GetMapping("/grades")
    @ResponseStatus(HttpStatus.OK)
    fun findAll() = gradeService.findAllGrades()

    @PostMapping("/grade")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCourse(@Valid @RequestBody grade: GradeDTO) =  gradeService.createGrade(grade)

    @DeleteMapping("grade/{gradeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteGrade(@PathVariable gradeId: Long) = gradeService.deleteGrade(gradeId)

}