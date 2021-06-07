package findmydrivers.springboot.findmydrivers.datasource.mock

import findmydrivers.springboot.findmydrivers.datasource.LocationDataSource
import findmydrivers.springboot.findmydrivers.model.Coordinates
import findmydrivers.springboot.findmydrivers.model.Location
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockLocationDataSource : LocationDataSource {
    private val coordinates = listOf(
        Coordinates(21, 23),
        Coordinates(21, 23),
        Coordinates(21, 23)
    )
    val locations = mutableListOf(
        Location(coordinates[0], "test"),
        Location(coordinates[1], "test2"),
        Location(coordinates[2], "test3")
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

    override fun deleteLocation(name: String) {
        val currentLocation = locations.firstOrNull { it.name == name }
            ?: throw NoSuchElementException("Could not find a bank with account number $name")

        locations.remove(currentLocation)
    }
}