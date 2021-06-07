package findmydrivers.springboot.findmydrivers.controller

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class LocationControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Nested
    @DisplayName("GET /locations")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetLocations {
        @Test
        fun `should rent all locations`() {
            mockMvc.get("/locations")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].lng") { value(12) }
                }
        }
    }

    @Nested
    @DisplayName("/locations?name")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetLocation {
        val baseUrl = "/locations"

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
}