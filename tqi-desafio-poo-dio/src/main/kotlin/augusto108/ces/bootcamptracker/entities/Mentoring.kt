package augusto108.ces.bootcamptracker.entities

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDate

@Entity
@DiscriminatorValue(value = "mentoring")
class Mentoring(
    @Column(name = "mentoring_date", nullable = true) var date: LocalDate? = null,
    @Column(name = "course_hours", nullable = true) var hours: Int? = null,
    description: String = "",
    details: String = "",
    id: Int = 0
) : Activity(description, details, id) {
    override fun toString(): String = "${super.toString()} (mentoring)"
}