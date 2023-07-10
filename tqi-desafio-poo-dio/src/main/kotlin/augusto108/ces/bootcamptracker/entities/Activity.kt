package augusto108.ces.bootcamptracker.entities

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*

@Entity
@Table(name = "activity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "activity_type", discriminatorType = DiscriminatorType.STRING)
@JsonPropertyOrder("id", "description", "details")
open class Activity(
    @Column(name = "activity_description", nullable = false, unique = true, length = 20) open var description: String,
    @Column(name = "activity_details", nullable = false, length = 80) open var details: String,
    id: Int
) : BaseEntity(id) {
    override fun toString(): String = description
}