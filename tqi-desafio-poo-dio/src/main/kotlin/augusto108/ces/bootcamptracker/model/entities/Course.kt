package augusto108.ces.bootcamptracker.model.entities

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDate

@Entity
@DiscriminatorValue(value = "course")
class Course(
    @Column(name = "mentoring_date", nullable = true) var date: LocalDate? = null,
    @Column(name = "course_hours", nullable = true) var hours: Int = 0,
    description: String = "",
    details: String = "",
    id: Int = 0
) : Activity(description, details, id) {

    override fun toString(): String = "${super.toString()} (course)"
}