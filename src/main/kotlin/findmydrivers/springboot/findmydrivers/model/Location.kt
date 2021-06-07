package findmydrivers.springboot.findmydrivers.model

data class Location(
    val coordinates: Coordinates,
    val name: String
)

data class Coordinates(
    val lng: Int,
    val lat: Int
)