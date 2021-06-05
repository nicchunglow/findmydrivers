package findmydrivers.springboot.findmydrivers.datasource.mock

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockLocationDataSourceTest {
    private val mockDataSource = MockLocationDataSource()

    @Test
    fun `should provide a collection of locations`() {
        val locations = mockDataSource.getLocations()
        assertThat(locations).isNotEmpty
    }

    @Test
    fun `should provide some mock data`() {
        val locations = mockDataSource.getLocations()
        assertThat(locations).allMatch { it.lng > 0 }
    }
}