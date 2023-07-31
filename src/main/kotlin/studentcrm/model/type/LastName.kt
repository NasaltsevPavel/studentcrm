package studentcrm.model.type

private const val LAST_NAME_IDENTIFIER = "lastName"

class LastName(firstName: String) : NameField(firstName) {
    init {
        verifyNameField(LAST_NAME_IDENTIFIER)
    }
}
fun String.toLastName() = LastName(this)
