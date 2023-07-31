package studentcrm.model.type

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.Embeddable
import jakarta.persistence.MappedSuperclass
import studentcrm.exceptions.InvalidInputException
import java.io.Serializable

const val NAME_FIELD_COURSE_PATTERN = "^[\\w|\\s]{2,35}$"
private val NAME_FIELD_COURSE_REGEX = Regex(NAME_FIELD_COURSE_PATTERN)

const val NAME_FIELD_STUDENT_PATTERN = "^[\\w|\\s]{2,20}$"
private val NAME_FIELD_STUDENT_REGEX = Regex(NAME_FIELD_STUDENT_PATTERN)

const val NAME_FIELD_LOCATION_AND_DEGREE_PATTERN = "^[\\w|\\s]{8,30}$"
private val NAME_FIELD_LOCATION_AND_DEGREE_REGEX = Regex(NAME_FIELD_LOCATION_AND_DEGREE_PATTERN)

@MappedSuperclass
@Embeddable
abstract class NameField(
    @get:JsonValue val value: String
) : Serializable {
    protected fun verifyNameField(identifier: String) {
        if ((identifier == "lastName") or (identifier == "firstName")) {
            if (value.isBlank()) {
                throw InvalidInputException("The $identifier can not be blank.")
            }
            if (!value.matches(NAME_FIELD_STUDENT_REGEX)) {
                throw InvalidInputException("The input [$value] doesn't match the regex pattern for a $identifier: $NAME_FIELD_STUDENT_PATTERN")
            }
        }
        if (identifier == "courseName") {
            if (value.isBlank()) {
                throw InvalidInputException("The $identifier can not be blank.")
            }
            if (!value.matches(NAME_FIELD_COURSE_REGEX)) {
                throw InvalidInputException("The input [$value] doesn't match the regex pattern for a $identifier: $NAME_FIELD_COURSE_PATTERN")
            }
        }
        if ((identifier == "locationName") or (identifier == "degreeName")) {
            if (value.isBlank()) {
                throw InvalidInputException("The $identifier can not be blank.")
            }
            if (!value.matches(NAME_FIELD_LOCATION_AND_DEGREE_REGEX)) {
                throw InvalidInputException("The input [$value] doesn't match the regex pattern for a $identifier: $NAME_FIELD_LOCATION_AND_DEGREE_PATTERN")
            }
        }
    }

    override fun toString() = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NameField

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}