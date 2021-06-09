package findmydrivers.springboot.findmydrivers.repository

import org.springframework.data.mongodb.repository.MongoRepository
import findmydrivers.springboot.findmydrivers.model.Location

interface LocationRepository : MongoRepository<Location, String> {
    fun findOneByName(name: String)
    fun deleteLocationByName(name: String)
}