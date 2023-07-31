package studentcrm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import studentcrm.model.location.LocationEntity
import java.util.Optional

@Repository
interface LocationRepository: JpaRepository<LocationEntity, Long> {

    fun findByLocationName(name: String): Optional<LocationEntity>

}