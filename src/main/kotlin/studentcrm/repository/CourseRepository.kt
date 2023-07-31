package studentcrm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import studentcrm.model.course.CourseEntity
import java.util.*

@Repository
interface CourseRepository: JpaRepository<CourseEntity, Long> {
    fun findByCourseName(name: String): Optional<CourseEntity>

    fun findByCourseID(id:Long): CourseEntity

}