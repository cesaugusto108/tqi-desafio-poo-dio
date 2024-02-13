package augusto108.ces.bootcamptracker.model.entities

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "bootcamp")
@JsonPropertyOrder("id", "description", "startDate", "finishDate", "details")
class Bootcamp @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    @Column(name = "bootcamp_description", nullable = false, unique = true, length = 30) var description: String = "",
    @Column(name = "bootcamp_details", nullable = false, length = 80) var details: String = "",
    @Column(name = "start_date") var startDate: LocalDateTime? = null,
    @Column(name = "finish_date") var finishDate: LocalDateTime? = null,
    id: Int = 0
) : BaseEntity(id) {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "bootcamp_activity",
        joinColumns = [JoinColumn(name = "bootcamp_id")],
        inverseJoinColumns = [JoinColumn(name = "activity_id")]
    )
    var activities: MutableSet<Activity> = HashSet()

    override fun toString(): String = description
}