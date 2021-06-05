package findmydrivers.springboot.findmydrivers.datasource.mock

import findmydrivers.springboot.findmydrivers.datasource.LocationDataSource
import findmydrivers.springboot.findmydrivers.model.Location
import org.springframework.stereotype.Repository

@Repository
class MockLocationDataSource : LocationDataSource {
    val locations = listOf(Location(12))
    override fun getLocations(): Collection<Location> = locations
}