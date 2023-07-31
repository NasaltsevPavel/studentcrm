package studentcrm.model.type

private const val FIRST_NAME_IDENTIFIER = "firstName"

class FirstName(firstName: String) : NameField(firstName) {
    init {
        verifyNameField(FIRST_NAME_IDENTIFIER)
    }
}

fun String.toFirstName() = FirstName(this)
