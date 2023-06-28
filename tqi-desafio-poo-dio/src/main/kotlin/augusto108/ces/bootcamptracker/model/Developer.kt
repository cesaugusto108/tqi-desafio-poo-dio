package augusto108.ces.bootcamptracker.model

import jakarta.persistence.*

@Entity
@DiscriminatorValue(value = "developer")
class Developer(
    @Column(name = "developer_level", nullable = false) var level: Int,
    name: Name,
    age: Int,
    email: String,
    id: Int
) : Person(name, age, email, id) {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "developer_bootcamp",
        joinColumns = [JoinColumn(name = "developer_id")],
        inverseJoinColumns = [JoinColumn(name = "bootcamp_id")]
    )
    val bootcamps: MutableSet<Bootcamp> = HashSet()

    override fun toString(): String = "($level) ${super.toString()}"
}