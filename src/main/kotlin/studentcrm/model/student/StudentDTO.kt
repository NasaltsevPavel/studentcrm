package studentcrm.model.student

import jakarta.validation.constraints.Email
import studentcrm.model.type.FirstName
import studentcrm.model.type.LastName


data class StudentDTO(
    var studentID: Long,
    var firstname: FirstName,
    var lastname: LastName,
    @field:Email
    var email: String,
    var degree: String,
    var coursesNames: Map<String, String>?,
    var grades: Map<String, String>?,
    var averageGrade: Double?
)
