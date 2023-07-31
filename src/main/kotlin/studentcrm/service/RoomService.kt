package studentcrm.service

import org.springframework.stereotype.Service
import studentcrm.exceptions.NotFoundException
import studentcrm.model.course.CourseEntity
import studentcrm.model.room.RoomDTO
import studentcrm.model.room.RoomEntity
import studentcrm.repository.LocationRepository
import studentcrm.repository.RoomRepository
import kotlin.jvm.optionals.getOrNull

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val locationRepository: LocationRepository
) {

    @OptIn(ExperimentalStdlibApi::class)
    fun createRoom(room: RoomDTO): RoomDTO {
        return locationRepository.findByLocationName(room.location).getOrNull()?.let { fromEntity(roomRepository.save(toEntity(room))) }
            ?: throw NotFoundException("Location with Name: ${room.location} not found")
    }

    fun findAllRooms(): List<RoomDTO> {

        val rooms: MutableList<RoomDTO> = arrayListOf()
        roomRepository.findAll().let { it.forEach { entity -> rooms.add(fromEntity(entity)) } }

        return rooms
    }


    private fun fromEntity(entity: RoomEntity): RoomDTO {
        val courseNames: ArrayList<String> = arrayListOf()

        entity.courseRoom.forEach { courseEntity -> courseNames.add(courseEntity.courseName) }

        return RoomDTO(entity.roomId, entity.roomNumber, entity.locationRoom.locationName, courseNames)
    }

    private fun toEntity(dto: RoomDTO): RoomEntity {
        val courseE: MutableList<CourseEntity> = mutableListOf()

        return RoomEntity(dto.roomId, dto.roomNumber,
            locationRepository.findByLocationName(dto.location).get(), courseE)
    }

}