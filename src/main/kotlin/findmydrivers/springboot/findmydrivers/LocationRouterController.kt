package findmydrivers.springboot.findmydrivers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("locations")
class LocationRouterController {
    @GetMapping("springboot")
    fun helloWorld(): String = "Hello this is Location"
}

