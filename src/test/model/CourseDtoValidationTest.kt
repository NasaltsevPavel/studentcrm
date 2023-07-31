package studentcrm.model

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import studentcrm.exceptions.InvalidInputException
import studentcrm.model.course.CourseDTO
import studentcrm.model.type.CourseName

class CourseDtoValidationTest () {

    private var validator: Validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should deserialize properly`() {
        val course = CourseDTO(1L, CourseName("Mathe"), mutableListOf())
        val violations: Set<ConstraintViolation<CourseDTO>> = validator.validate(course)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should create a ConstraintViolation - name too long`() {

        assertThrows<InvalidInputException> {
            val course = CourseDTO(1L, CourseName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"), mutableListOf()) // size > 35
        }


    }

    @Test
    fun `should create a ConstraintViolation - name too short`() {
        assertThrows<InvalidInputException> {
            val course = CourseDTO(1L, CourseName("a"), mutableListOf()) // size < 2
        }

    }






}