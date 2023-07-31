package studentcrm.controller

import jakarta.annotation.security.RolesAllowed
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import studentcrm.model.location.LocationDTO
import studentcrm.service.LocationService

@RestController
@RequestMapping("/location-api")
class LocationController(val locationService: LocationService) {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/locations")
    @ResponseStatus(HttpStatus.OK)
    fun findAll() = locationService.findAllLocations()

    @PostMapping("/location")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCourse(@Valid @RequestBody location: LocationDTO) = locationService.createLocation(location)

}