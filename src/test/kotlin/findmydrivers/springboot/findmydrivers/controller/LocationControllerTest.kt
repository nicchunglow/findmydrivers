package findmydrivers.springboot.findmydrivers.controller

import com.fasterxml.jackson.databind.ObjectMapper
import findmydrivers.springboot.findmydrivers.model.Location
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class LocationControllerTest @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper
) {

    val baseUrl = "/locations"

    @Nested
    @DisplayName("GET /locations")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetLocations {
        @Test
        fun `should rent all locations`() {
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].lng") { value(12) }
                }
        }
    }

    @Nested
    @DisplayName("GET /locations?name")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetLocation {
        @Test
        fun `should return location with the given id`() {
            val name = "test"
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
            mockMvc.get("$baseUrl/$name")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
                .andExpect { content { } }
        }
    }

    @Nested
    @DisplayName("POST /locations")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostLocation {
        @Test
        fun `should POST new location`() {
            val newLocation = Location(152, "MyPlace")
            val performPost = mockMvc.post(baseUrl) {
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
        fun `should return BAD REQUEST if location with name already exist`() {
            val invalidLocation = Location(12, "test")
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidLocation)
            }
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }
}