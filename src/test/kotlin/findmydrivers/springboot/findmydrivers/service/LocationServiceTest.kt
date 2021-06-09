package findmydrivers.springboot.findmydrivers.service

import findmydrivers.springboot.findmydrivers.repository.LocationRepository
import io.mockk.*
import org.junit.jupiter.api.Test

internal class LocationServiceTest {
    private val locationRepository: LocationRepository = mockk(relaxed = true)
    private val locationService = LocationService(locationRepository)

    @Test
    fun `should call its data source to retrieve locations`() {
        locationService.getLocations()
        verify(exactly = 1) { locationRepository.findAll() }
    }
}