package studentcrm.model.type


private const val DEGREE_NAME_IDENTIFIER = "degreeName"


    class DegreeName(degreeName: String) : NameField(degreeName) {
        init {
            verifyNameField(DEGREE_NAME_IDENTIFIER)
        }
    }

    fun String.toDegreeName() = DegreeName(this)
