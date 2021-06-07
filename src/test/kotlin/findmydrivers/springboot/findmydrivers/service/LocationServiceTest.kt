package findmydrivers.springboot.findmydrivers.service

import findmydrivers.springboot.findmydrivers.datasource.LocationDataSource
import io.mockk.*
import org.junit.jupiter.api.Test

internal class LocationServiceTest {
    private val dataSource: LocationDataSource = mockk(relaxed = true)
    private val locationService = LocationService(dataSource)

    @Test
    fun `should call its data source to retrieve locations`() {
        locationService.getLocations()
        verify(exactly = 1) { dataSource.getLocations() }
    }
}