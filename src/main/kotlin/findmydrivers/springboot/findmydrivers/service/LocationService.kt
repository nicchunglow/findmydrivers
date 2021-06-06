package findmydrivers.springboot.findmydrivers.service

import findmydrivers.springboot.findmydrivers.datasource.LocationDataSource
import findmydrivers.springboot.findmydrivers.model.Location
import org.springframework.stereotype.Service

@Service
class LocationService(private val dataSource: LocationDataSource) {
    fun getLocations(): Collection<Location> = dataSource.getLocations()
}