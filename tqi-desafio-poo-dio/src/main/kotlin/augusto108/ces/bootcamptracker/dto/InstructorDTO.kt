package augusto108.ces.bootcamptracker.dto

import augusto108.ces.bootcamptracker.entities.Name

class InstructorDTO(
    var level: Int? = null,
    name: Name = Name(),
    age: Int = 0,
    email: String = "",
    username: String = "",
    id: Int = 0
) : PersonDTO(name, age, email, username, id)