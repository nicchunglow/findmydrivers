package findmydrivers.springboot.findmydrivers.service

import findmydrivers.springboot.findmydrivers.repository.LocationRepository
import findmydrivers.springboot.findmydrivers.model.Location
import findmydrivers.springboot.findmydrivers.request.LocationRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LocationService(

    @Autowired
    private val locationRepository: LocationRepository,
) {

    fun getLocations(): Collection<Location> = locationRepository.findAll()

    fun getLocation(name: String) {
        return locationRepository.findOneByName(name)
    }

    fun postLocation(request: LocationRequest): Location = locationRepository.save(
        Location(
            name = request.name,
            coordinates = request.coordinates
        )
    )

    fun deleteLocation(name: String) {
        return locationRepository.findOneByName(name)
    }
}