package augusto108.ces.bootcamptracker.model.dto

import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import augusto108.ces.bootcamptracker.model.entities.Name
import java.util.*

class DeveloperDTO(
    var level: Int = 0,
    name: Name = Name(),
    age: Int = 0,
    email: String = "",
    username: String = "",
    active: Boolean = true,
    id: UUID? = null
) : PersonDTO(name, age, email, username, active, id) {
    var bootcamps: MutableSet<Bootcamp> = HashSet()

    override fun toString(): String = "($level) ${super.toString()}"
}