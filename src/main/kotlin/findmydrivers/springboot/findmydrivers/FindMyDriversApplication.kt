package findmydrivers.springboot.findmydrivers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FindMyDriversApplication

fun main(args: Array<String>) {
	runApplication<FindMyDriversApplication>(*args)
}
