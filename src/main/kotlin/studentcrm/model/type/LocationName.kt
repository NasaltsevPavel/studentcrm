package studentcrm.model.type


private const val LOCATION_NAME_IDENTIFIER = "locationName"


class LocationName(locationName: String) : NameField(locationName) {
    init {
        verifyNameField(LOCATION_NAME_IDENTIFIER)
    }
}

fun String.toLocationName() = LocationName(this)