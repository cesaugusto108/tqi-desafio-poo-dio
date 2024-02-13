package augusto108.ces.bootcamptracker.model.entities

import augusto108.ces.bootcamptracker.model.datatypes.Name
import com.fasterxml.jackson.annotation.JsonCreator
import jakarta.persistence.*
import java.util.*

@Entity
@DiscriminatorValue(value = "developer")
class Developer @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    @Column(name = "developer_level", nullable = true) var level: Int = 0,
    name: Name = Name(),
    age: Int = 0,
    email: String = "",
    username: String = "",
    password: String = "",
    active: Boolean = true,
    id: UUID? = null
) : Person(name, age, email, username, password, active, id) {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "developer_bootcamp",
        joinColumns = [JoinColumn(name = "developer_id")],
        inverseJoinColumns = [JoinColumn(name = "bootcamp_id")]
    )
    var bootcamps: MutableSet<Bootcamp> = HashSet()

    override fun toString(): String = "($level) ${super.toString()}"
}