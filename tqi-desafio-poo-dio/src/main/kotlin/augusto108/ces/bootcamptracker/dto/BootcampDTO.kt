package augusto108.ces.bootcamptracker.dto

import augusto108.ces.bootcamptracker.entities.Activity
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

@JsonPropertyOrder("id", "description", "details", "startDate", "finishDate", "activities")
class BootcampDTO(
    var description: String = "",
    var details: String = "",
    var startDate: LocalDateTime? = null,
    var finishDate: LocalDateTime? = null,
    id: Int = 0
) : BaseDto(id) {
    var activities: MutableSet<Activity> = HashSet()

    override fun toString(): String = description
}