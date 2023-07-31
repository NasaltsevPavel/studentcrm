package studentcrm.model.location

import studentcrm.model.type.LocationName

data class LocationDTO (
    var locationId: Long,
    var locationName: LocationName,
    var locationRooms: List<Int>?
)
