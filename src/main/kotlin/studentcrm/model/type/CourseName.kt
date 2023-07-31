package studentcrm.model.type

private const val COURSE_NAME_IDENTIFIER = "courseName"

class CourseName(courseName: String) : NameField(courseName) {
    init {
        verifyNameField(COURSE_NAME_IDENTIFIER)
    }
}

fun String.toCourseName() = CourseName(this)
