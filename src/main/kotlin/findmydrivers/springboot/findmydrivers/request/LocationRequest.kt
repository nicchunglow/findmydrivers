package findmydrivers.springboot.findmydrivers.request

import findmydrivers.springboot.findmydrivers.model.Coordinates

class LocationRequest(
    val name: String,
    val coordinates: Coordinates
)

