package studentcrm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import studentcrm.model.degree.DegreeEntity
import java.util.Optional

@Repository
interface DegreeRepository: JpaRepository<DegreeEntity,Long> {

    fun findByDegreeName(name: String): Optional<DegreeEntity>
}