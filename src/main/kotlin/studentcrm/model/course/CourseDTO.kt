package studentcrm.model.course

import studentcrm.model.type.CourseName

data class CourseDTO(
    val courseID: Long,
    val courseName: CourseName,
    var degrees: List<String>?,
    var room: Int,
    val location: String?,
    var averageGrade: Double,
    val students: List<String>?,
    var grades: List<Double>?
)