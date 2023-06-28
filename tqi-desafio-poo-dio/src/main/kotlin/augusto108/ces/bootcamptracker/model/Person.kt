package augusto108.ces.bootcamptracker.model

import jakarta.persistence.*

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
open class Person(
    @Embedded open var name: Name,
    @Column(name = "person_age") open var age: Int,
    @Column(name = "email", nullable = false, length = 30) open var email: String,
    id: Int
) : BaseEntity(id) {
    override fun toString(): String = "$name ($email)"
}