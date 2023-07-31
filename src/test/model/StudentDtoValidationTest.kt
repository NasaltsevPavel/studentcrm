package studentcrm.model

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import studentcrm.exceptions.InvalidInputException
import studentcrm.model.student.StudentDTO
import studentcrm.model.type.FirstName
import studentcrm.model.type.LastName

class StudentDtoValidationTest {


    private var validator: Validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should deserialize properly`() {
        val student = StudentDTO(1L, FirstName("Pavel"), LastName("Nasaltsev"),"nasaltsev@web.de",
            mutableListOf())
        val violations: Set<ConstraintViolation<StudentDTO>> = validator.validate(student)
        Assertions.assertTrue(violations.isEmpty())
    }

    @Test
    fun `should create a ConstraintViolation - firstname too long`() {

        assertThrows<InvalidInputException> {
            val student = StudentDTO(1L, FirstName("PavelPavelPavelPavelPavel"), LastName("Nasaltsev"),"nasaltsev@web.de",
                mutableListOf())// size > 20
        }

    }

    @Test
    fun `should create a ConstraintViolation - firstname too short`() {
        assertThrows<InvalidInputException> {
            val student = StudentDTO(1L,FirstName("P"), LastName("Nasaltsev"),"nasaltsev@web.de",
                mutableListOf()) // size < 2
        }
    }

    @Test
    fun `should create a ConstraintViolation - lastname too long`() {
        assertThrows<InvalidInputException> {
            val student = StudentDTO(1L, FirstName("Pavel"), LastName("NasaltsevNasaltsevNasaltsev"),"nasaltsev@web.de",
                mutableListOf())// size > 20
        }

    }

    @Test
    fun `should create a ConstraintViolation - lastname too short`() {
        assertThrows<InvalidInputException> {
            val student = StudentDTO(1L,FirstName("Pavel"), LastName("N"),"nasaltsev@web.de",
                mutableListOf()) // size < 2
        }
    }

    @Test
    fun `should create a ConstraintViolation - wrong email`() {
        val student = StudentDTO(1L,FirstName("Pavel"), LastName("Nasaltsev"),"nasaltsevmail",
            mutableListOf())
        val violations: Set<ConstraintViolation<StudentDTO>> = validator.validate(student)
        Assertions.assertTrue(violations.isNotEmpty())
    }


}