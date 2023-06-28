package augusto108.ces.bootcamptracker.model

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDate

@Entity
@DiscriminatorValue(value = "mentoring")
class Mentoring(
    @Column(name = "mentoring_date", nullable = false) var date: LocalDate,
    description: String,
    details: String,
    id: Int
) : Activity(description, details, id) {
    override fun toString(): String = "$description (mentoring)"
}