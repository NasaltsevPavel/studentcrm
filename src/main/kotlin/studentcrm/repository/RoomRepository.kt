package studentcrm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import studentcrm.model.room.RoomEntity
import java.util.Optional

@Repository
interface RoomRepository: JpaRepository<RoomEntity,Long> {

    fun findByRoomNumber(number: Int): Optional<RoomEntity>
}