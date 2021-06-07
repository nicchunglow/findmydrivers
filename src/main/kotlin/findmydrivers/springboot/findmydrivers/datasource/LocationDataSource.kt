package findmydrivers.springboot.findmydrivers.datasource

import findmydrivers.springboot.findmydrivers.model.Location

interface LocationDataSource {
    fun getLocations(): Collection<Location>
    fun getLocation(name: String): Location
    fun postLocation(location: Location): Location
    fun deleteLocation(name: String)
}