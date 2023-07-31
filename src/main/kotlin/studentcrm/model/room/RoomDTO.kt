package studentcrm.model.room

import jakarta.validation.constraints.Size

data class RoomDTO (
    var roomId: Long,
    @Size(min = 3, max = 3)
    var roomNumber: Int,
    var location: String,
    var course: List<String>?
)