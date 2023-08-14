package augusto108.ces.bootcamptracker.model.dto

import augusto108.ces.bootcamptracker.model.entities.Name
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("id", "name", "age", "username", "email")
abstract class PersonDTO(
    var name: Name = Name(),
    var age: Int = 0,
    var email: String = "",
    var username: String = "",
    id: Int
) : BaseDto(id) {
    override fun toString(): String = "$name ($email)"
}