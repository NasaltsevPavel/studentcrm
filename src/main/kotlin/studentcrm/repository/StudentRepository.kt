package studentcrm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import studentcrm.model.student.StudentEntity
import java.util.*

@Repository
interface StudentRepository: JpaRepository<StudentEntity,Long> {
    fun findByStudentID(id: Long): StudentEntity
    fun findByLastName(lastname: String): Optional<StudentEntity>
}