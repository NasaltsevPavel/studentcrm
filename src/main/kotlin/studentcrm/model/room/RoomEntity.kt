package studentcrm.model.room

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Range
import studentcrm.model.course.CourseEntity
import studentcrm.model.location.LocationEntity


@Entity
@Table(name = "rooms")
class RoomEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "roomId")
    var roomId: Long,

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "locationNumber")
    var roomNumber: Int,

    @ManyToOne
    @JoinColumn(name="locationId")
    var locationRoom: LocationEntity,

    @OneToMany(mappedBy = "room")
    var courseRoom: List<CourseEntity>

)