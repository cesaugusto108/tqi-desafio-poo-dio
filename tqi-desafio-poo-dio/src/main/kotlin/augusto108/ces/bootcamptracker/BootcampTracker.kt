package augusto108.ces.bootcamptracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Profile

@SpringBootApplication
@Profile("dev")
class Bootcamptracker

fun main() {
    runApplication<Bootcamptracker>()
}