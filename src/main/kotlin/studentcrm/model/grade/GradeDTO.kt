package studentcrm.model.grade

import org.hibernate.validator.constraints.Range

data class GradeDTO (
    val gradeId: Long,
    var student: String,
    var course: String,
    @Range(min=1, max=5)
    var grade: Double,
    var courseGrade: String?

)