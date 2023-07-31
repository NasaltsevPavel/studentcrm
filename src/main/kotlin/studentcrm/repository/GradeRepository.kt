package studentcrm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import studentcrm.model.grade.GradeEntity

@Repository
interface GradeRepository: JpaRepository<GradeEntity,Long> {
}