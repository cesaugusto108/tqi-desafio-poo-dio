package augusto108.ces.bootcamptracker.model.entities

import augusto108.ces.bootcamptracker.model.datatypes.Name
import com.fasterxml.jackson.annotation.JsonCreator
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.util.*

@Entity
@DiscriminatorValue(value = "instructor")
class Instructor @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    @Column(name = "developer_level", nullable = true) var level: Int? = null,
    name: Name = Name(),
    age: Int = 0,
    email: String = "",
    username: String = "",
    password: String = "",
    active: Boolean = true,
    id: UUID? = null
) : Person(name, age, email, username, password, active, id)