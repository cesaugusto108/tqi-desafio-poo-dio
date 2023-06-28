package augusto108.ces.bootcamptracker.model

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue(value = "course")
class Course(
    @Column(name = "course_hours", nullable = false) var hours: Int,
    description: String,
    details: String,
    id: Int
) : Activity(description, details, id) {
    override fun toString(): String = "$description (course)"
}