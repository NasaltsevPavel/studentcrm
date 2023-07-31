package studentcrm.integration

import org.assertj.core.api.Assertions.assertThat
import org.flywaydb.test.annotation.FlywayTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import studentcrm.model.student.StudentEntity
import studentcrm.repository.StudentRepository

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FlywayTest
class StudentRepositoryIntegrationTest
{
    @Autowired
    private lateinit var studentRepository: StudentRepository
    private lateinit var studentEntity: StudentEntity

    @BeforeEach
    fun prepareTests() {
        studentEntity = StudentEntity(1L, "Firstname", "Lastname", "email@email.com", mutableListOf())

    }

    @Test
    fun testFindByStudentID() {
        studentRepository.save(studentEntity)
        val found = studentRepository.findByStudentID(studentEntity.studentID)
        assertThat(found.firstName).isEqualTo(studentEntity.firstName)
    }

    @Test
    fun testFindByLastName() {
        studentRepository.save(studentEntity)
        val found = studentRepository.findByLastName(studentEntity.lastName)
        assertThat(found.get().studentID).isEqualTo(studentEntity.studentID)
    }




}