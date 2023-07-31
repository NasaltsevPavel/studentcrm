package studentcrm.model.location

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import studentcrm.model.room.RoomEntity

@Entity
@Table(name = "locations")
class LocationEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "locationId")
    var locationId: Long,

    @NotBlank
    @Size(min = 2, max = 35)
    @Column(name = "locationName")
    var locationName: String,

    @OneToMany(mappedBy = "locationRoom")
    var locationRooms: List<RoomEntity>?,
/*
    @ElementCollection
    @CollectionTable(name = "order_item_mapping")
    @MapKeyColumn(name = "item_name")
    @Column(name = "price")
    var testMap: Map<Int,Int>
*/
)