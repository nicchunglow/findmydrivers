package findmydrivers.springboot.findmydrivers.controller

import findmydrivers.springboot.findmydrivers.model.Location
import findmydrivers.springboot.findmydrivers.service.LocationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/locations")
class LocationController(private val service: LocationService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @GetMapping
    fun getlocations(): Collection<Location> = service.getLocations()

    @GetMapping("/{name}")
    fun getLocation(@PathVariable name: String) = service.getBank(name)
}