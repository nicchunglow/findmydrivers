package findmydrivers.springboot.findmydrivers.controller

import findmydrivers.springboot.findmydrivers.model.Location
import findmydrivers.springboot.findmydrivers.request.LocationRequest
import findmydrivers.springboot.findmydrivers.service.LocationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/locations")
class LocationController(private val service: LocationService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getAllLocations(): Collection<Location> = service.getLocations()

    @GetMapping("/{name}")
    fun getLocation(@PathVariable name: String) = service.getLocation(name)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postLocation(@RequestBody request: LocationRequest): Location {
        if (request.name.isEmpty()) {
            throw IllegalArgumentException("Please fill in all details")
        } else {
            return service.postLocation(LocationRequest(request.name, request.coordinates))
        }
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLocation(@PathVariable name: String): Unit = service.deleteLocation(name)
}