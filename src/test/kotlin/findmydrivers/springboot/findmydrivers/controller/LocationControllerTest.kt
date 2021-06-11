package findmydrivers.springboot.findmydrivers.controller

import com.fasterxml.jackson.databind.ObjectMapper
import findmydrivers.springboot.findmydrivers.model.Coordinates
import findmydrivers.springboot.findmydrivers.model.Location
import findmydrivers.springboot.findmydrivers.repository.LocationRepository
import findmydrivers.springboot.findmydrivers.service.LocationService
import io.mockk.clearAllMocks
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@SpringBootTest
@AutoConfigureMockMvc
internal class LocationControllerTest @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper,
) {
    private val coordinates = listOf(
        Coordinates(12, 23),
        Coordinates(23, 23),
        Coordinates(45, 23)
    )
    val locations = mutableListOf(
        Location(coordinates[0], "test"),
        Location(coordinates[1], "test2"),
        Location(coordinates[2], "test3")
    )

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun service() = mock<LocationService>()

        @Bean
        fun repository() = mock<LocationRepository>()
    }

    @Autowired
    private lateinit var service: LocationService

    @BeforeEach
    internal fun setUpMocks() {
        clearAllMocks()
        MockitoAnnotations.openMocks(this)
    }

    val baseUrl = "/locations"

    @Nested
    @DisplayName("GET /locations")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetLocations {
        @Test
        fun `should get all locations`() {
            whenever(service.getLocations()).thenReturn(locations)
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].coordinates.lng") { value(12) }
                }
        }
    }

    @Nested
    @DisplayName("GET /locations?name")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetLocation {
        @Test
        fun `should return location with the given id`() {
            val name = "test"
            whenever(service.getLocation(name)).thenReturn(locations[0])
            mockMvc.get("$baseUrl/$name")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.name") { value("test") }
                }
        }

        @Test
        fun `should return status 404 if name not found`() {
            val name = "fake_name"
            whenever(service.getLocation(name)).thenThrow(NoSuchElementException())
            mockMvc.get("$baseUrl/$name")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
                .andExpect { content { } }
        }
    }

    @Nested
    @DisplayName("POST /locations/create")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostLocation {
        @Test
        fun `should POST new location`() {
            val newCoordinates = Coordinates(21, 23)
            val newLocation = Location(newCoordinates, "MyPlace")
            val performPost = mockMvc.post("$baseUrl/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newLocation)
            }
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.name") { value("MyPlace") }
                }
        }

        @Test
        fun `should return BAD REQUEST if location with name field is empty`() {
            val newCoordinates = Coordinates(21, 23)
            val invalidLocation = Location(newCoordinates, "test")
            whenever(service.postLocation(any())).thenThrow(NoSuchElementException())
            mockMvc.post("$baseUrl/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidLocation)
            }.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    @DisplayName("DELETE /locations/{name}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class DeleteExistingLocation {
        @Test
        fun `should delete the location with the given name`() {
            val name = "test"
            mockMvc.delete("$baseUrl/$name")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("$baseUrl/$name")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if no location with given name exists`() {
            val invalidName = "does_not_exist"
            whenever(service.deleteLocation(invalidName)).thenThrow(NoSuchElementException())
            mockMvc.delete("$baseUrl/$invalidName")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}