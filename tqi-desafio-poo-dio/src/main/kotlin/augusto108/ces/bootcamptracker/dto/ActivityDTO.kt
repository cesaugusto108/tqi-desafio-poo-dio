package augusto108.ces.bootcamptracker.dto

import augusto108.ces.bootcamptracker.entities.BaseEntity
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("id", "description", "details")
open class ActivityDTO(
    var description: String = "",
    var details: String = "",
    id: Int
) : BaseEntity(id) {
    override fun toString(): String = description
}