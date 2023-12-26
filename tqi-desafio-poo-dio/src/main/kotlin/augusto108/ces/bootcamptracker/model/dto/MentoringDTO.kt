package augusto108.ces.bootcamptracker.model.dto

import java.time.LocalDate

class MentoringDTO(
    var date: LocalDate? = null,
    var hours: Int? = null,
    description: String = "",
    details: String = "",
    id: Int = 0
) : ActivityDTO(description, details, id) {

    override fun toString(): String = "${super.toString()} (mentoring)"
}