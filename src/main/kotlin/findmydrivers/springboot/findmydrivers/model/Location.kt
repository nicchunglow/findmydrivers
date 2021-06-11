package findmydrivers.springboot.findmydrivers.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("location")
data class Location(
    val coordinates: Coordinates,
    @Id
    @Indexed(unique = true)
    val name: String
)

@Document
data class Coordinates(
    val lng: Number,
    val lat: Number
)