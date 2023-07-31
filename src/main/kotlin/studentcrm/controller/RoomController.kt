package studentcrm.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import studentcrm.model.location.LocationDTO
import studentcrm.model.room.RoomDTO
import studentcrm.service.RoomService


@RestController
@RequestMapping("/room-api")
class RoomController(private val roomService: RoomService) {

    @GetMapping("/rooms")
    @ResponseStatus(HttpStatus.OK)
    fun findAll() = roomService.findAllRooms()

    @PostMapping("/room")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCourse(@Valid @RequestBody room: RoomDTO) = roomService.createRoom(room)

}