package augusto108.ces.bootcamptracker.model.dto

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("id", "description", "details")
abstract class ActivityDTO(var description: String = "", var details: String = "", id: Int) : BaseDto(id) {

    override fun toString(): String = description
}