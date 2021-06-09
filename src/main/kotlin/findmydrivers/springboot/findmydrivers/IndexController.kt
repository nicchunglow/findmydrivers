package findmydrivers.springboot.findmydrivers

import org.apache.tomcat.util.json.JSONParser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/")
class IndexController {
    @GetMapping
    fun index() = """{
        0: "GET   /locations",
        1: "POST /locations/create",
        2: "DELETE /locations/:name",
        3: "GET /drivers",
    }"""
}

