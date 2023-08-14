package augusto108.ces.bootcamptracker.model.dto

import augusto108.ces.bootcamptracker.model.entities.Name

class InstructorDTO(
    var level: Int? = null,
    name: Name = Name(),
    age: Int = 0,
    email: String = "",
    username: String = "",
    id: Int = 0
) : PersonDTO(name, age, email, username, id)