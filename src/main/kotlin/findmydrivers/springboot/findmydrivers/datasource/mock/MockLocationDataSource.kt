package findmydrivers.springboot.findmydrivers.datasource.mock

import findmydrivers.springboot.findmydrivers.datasource.LocationDataSource
import findmydrivers.springboot.findmydrivers.model.Location
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockLocationDataSource : LocationDataSource {
    val locations = mutableListOf(
        Location(12, "test"),
        Location(20, "test2"),
        Location(30, "test3")
    )

    override fun getLocations(): Collection<Location> = locations

    override fun getLocation(name: String): Location =
        locations.firstOrNull { it.name == name }
            ?: throw NoSuchElementException("Could not find location with name of $name")

    override fun postLocation(location: Location): Location {
        if (locations.any { it.name == location.name }) {
            throw IllegalArgumentException("Location with ${location.name} already exist")
        } else {
            locations.add(location)
            return location
        }
    }
}