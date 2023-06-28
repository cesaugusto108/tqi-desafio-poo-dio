package augusto108.ces.bootcamptracker.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "bootcamp")
class Bootcamp(
    @Column(name = "bootcamp_description", nullable = false, unique = true, length = 30) var description: String,
    @Column(name = "bootcamp_details", nullable = false, length = 80) var details: String,
    @Column(name = "start_date", nullable = false) var startDate: LocalDateTime,
    @Column(name = "finish_date", nullable = false) var finishDate: LocalDateTime,
    id: Int
) : BaseEntity(id) {
    override fun toString(): String = description
}