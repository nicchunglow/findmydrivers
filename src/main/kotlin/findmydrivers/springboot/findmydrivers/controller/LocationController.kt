package findmydrivers.springboot.findmydrivers.controller

import findmydrivers.springboot.findmydrivers.model.Location
import findmydrivers.springboot.findmydrivers.service.LocationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/locations")
class LocationController(private val service: LocationService) {
    @GetMapping
    fun getlocations(): Collection<Location> = service.getLocations()
}