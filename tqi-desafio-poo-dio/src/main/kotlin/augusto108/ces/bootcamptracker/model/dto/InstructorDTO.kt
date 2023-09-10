package augusto108.ces.bootcamptracker.model.dto

import augusto108.ces.bootcamptracker.model.entities.Name
import java.util.*

class InstructorDTO(
    var level: Int? = null,
    name: Name = Name(),
    age: Int = 0,
    email: String = "",
    username: String = "",
    active: Boolean = true,
    id: UUID? = null
) : PersonDTO(name, age, email, username, active, id)