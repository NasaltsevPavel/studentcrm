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
import studentcrm.model.course.CourseEntity
import studentcrm.repository.CourseRepository

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FlywayTest
class CourseRepositoryIntegrationTest
{
    @Autowired
    private lateinit var courseRepository: CourseRepository
    private lateinit var courseEntity: CourseEntity

    @BeforeEach
    fun prepareTests() {
        courseEntity = CourseEntity(1L, "Name", mutableListOf())
    }

    @Test
    fun findByCourseID() {
        courseRepository.save(courseEntity)
        val found = courseRepository.findByCourseID(courseEntity.courseID)
        assertThat(found.courseName).isEqualTo(courseEntity.courseName)
    }

    @Test
    fun testFindByLastName() {
        courseRepository.save(courseEntity)
        val found = courseRepository.findByCourseName(courseEntity.courseName)
        assertThat(found.get().courseID).isEqualTo(courseEntity.courseID)
    }

}