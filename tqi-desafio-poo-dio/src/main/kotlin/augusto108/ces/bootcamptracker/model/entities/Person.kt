package augusto108.ces.bootcamptracker.model.entities

import augusto108.ces.bootcamptracker.model.datatypes.Name
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
abstract class Person(
    @Embedded open var name: Name = Name(),
    @Column(name = "person_age") open var age: Int = 0,
    @Column(name = "email", nullable = false, length = 30) open var email: String = "",
    @Column(name = "username", nullable = false, length = 30) open var username: String = "",
    @Column(name = "password", nullable = false) open var password: String = "",
    @Column(name = "active", nullable = false) open var active: Boolean = true,
    id: UUID? = null
) : PersonBaseEntity(id) {

    init {
        this.password = BCryptPasswordEncoder().encode(password)
    }

    override fun toString(): String = "$name ($email)"
}