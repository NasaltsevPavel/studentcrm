package studentcrm.service

import org.springframework.stereotype.Service
import studentcrm.model.location.LocationDTO
import studentcrm.model.location.LocationEntity
import studentcrm.model.room.RoomEntity
import studentcrm.model.type.toLocationName
import studentcrm.repository.LocationRepository

@Service
class LocationService(private val locationRepository: LocationRepository) {

    fun createLocation(location: LocationDTO) = fromEntity(locationRepository.save(toEntity(location)))

    fun findAllLocations(): List<LocationDTO> {

        val locations: MutableList<LocationDTO> = arrayListOf()
         locationRepository.findAll().let { it.forEach { entity -> locations.add(fromEntity(entity))  } }

        return locations
    }

    private fun fromEntity(entity: LocationEntity): LocationDTO {
        val rooms: ArrayList<Int> = arrayListOf()

        entity.locationRooms?.forEach { roomEntity -> rooms.add(roomEntity.roomNumber) }

        return LocationDTO(entity.locationId, entity.locationName.toLocationName(), rooms)
    }

    private fun toEntity(dto: LocationDTO): LocationEntity {
        val rooms: MutableList<RoomEntity> = mutableListOf()

        return LocationEntity(dto.locationId, dto.locationName.value, rooms)
    }
}

