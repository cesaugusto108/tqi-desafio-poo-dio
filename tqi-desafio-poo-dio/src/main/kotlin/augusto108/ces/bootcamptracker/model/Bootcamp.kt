package augusto108.ces.bootcamptracker.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "bootcamp")
class Bootcamp(
    @Column(name = "bootcamp_description", nullable = false, unique = true, length = 30) var description: String,
    @Column(name = "bootcamp_details", nullable = false, length = 80) var details: String,
    @Column(name = "start_date") var startDate: LocalDateTime,
    @Column(name = "finish_date") var finishDate: LocalDateTime,
    id: Int
) : BaseEntity(id) {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "bootcamp_activity",
        joinColumns = [JoinColumn(name = "bootcamp_id")],
        inverseJoinColumns = [JoinColumn(name = "activity_id")]
    )
    val activities: MutableSet<Activity> = HashSet()

    override fun toString(): String = description
}