package augusto108.ces.bootcamptracker.dto

import augusto108.ces.bootcamptracker.entities.BaseEntity
import augusto108.ces.bootcamptracker.entities.Name

open class PersonDTO(
    var name: Name = Name(),
    var age: Int = 0,
    var email: String = "",
    var username: String = "",
    id: Int
) : BaseEntity(id) {
    override fun toString(): String = "$name ($email)"
}