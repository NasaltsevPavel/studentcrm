package studentcrm.model.degree

import studentcrm.model.type.DegreeName

data class DegreeDTO (
    var degreeID: Long,
    var degreeName: DegreeName,
    var degreeStudent: List<String>?,
    val degreeCourse: List<String>?
)