package augusto108.ces.bootcamptracker.dto

import augusto108.ces.bootcamptracker.entities.Bootcamp
import augusto108.ces.bootcamptracker.entities.Name

class DeveloperDTO(
    var level: Int = 0,
    name: Name = Name(),
    age: Int = 0,
    email: String = "",
    username: String = "",
    var bootcamps: MutableSet<Bootcamp> = HashSet(),
    id: Int = 0
) : PersonDTO(name, age, email, username, id) {
    override fun toString(): String = "($level) ${super.toString()}"
}