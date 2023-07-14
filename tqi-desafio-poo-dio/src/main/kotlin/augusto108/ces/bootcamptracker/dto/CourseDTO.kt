package augusto108.ces.bootcamptracker.dto

import java.time.LocalDate

class CourseDTO(
    var date: LocalDate? = null,
    var hours: Int = 0,
    description: String = "",
    details: String = "",
    id: Int = 0
) : ActivityDTO(description, details, id) {
    override fun toString(): String = "${super.toString()} (course)"
}