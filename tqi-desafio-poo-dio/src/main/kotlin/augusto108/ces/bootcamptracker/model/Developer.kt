package augusto108.ces.bootcamptracker.model

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue(value = "developer")
class Developer(
    @Column(name = "developer_level", nullable = false) var level: Int,
    name: Name,
    age: Int,
    email: String,
    id: Int
) : Person(name, age, email, id) {
    override fun toString(): String = "($level) ${super.toString()}"
}