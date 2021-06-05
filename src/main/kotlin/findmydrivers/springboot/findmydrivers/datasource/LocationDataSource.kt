package findmydrivers.springboot.findmydrivers.datasource

import findmydrivers.springboot.findmydrivers.model.Location

interface LocationDataSource {
    fun getLocations(): Collection<Location>
}