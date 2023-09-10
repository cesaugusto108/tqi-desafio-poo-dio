package augusto108.ces.bootcamptracker.model.dto

import augusto108.ces.bootcamptracker.model.entities.Name
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.*

@JsonPropertyOrder("id", "name", "age", "username", "email")
abstract class PersonDTO(
    var name: Name = Name(),
    var age: Int = 0,
    var email: String = "",
    var username: String = "",
    var active: Boolean = true,
    id: UUID? = null
) : PersonBaseDto(id) {
    override fun toString(): String = "$name ($email)"
}