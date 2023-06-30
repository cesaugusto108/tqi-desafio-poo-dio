package augusto108.ces.bootcamptracker.model

import jakarta.persistence.*

@Entity
@DiscriminatorValue(value = "developer")
class Developer(
    @Column(name = "developer_level", nullable = true) var level: Int = 0,
    name: Name = Name(),
    age: Int = 0,
    email: String = "",
    id: Int = 0
) : Person(name, age, email, id) {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "developer_bootcamp",
        joinColumns = [JoinColumn(name = "developer_id")],
        inverseJoinColumns = [JoinColumn(name = "bootcamp_id")]
    )
    var bootcamps: MutableSet<Bootcamp> = HashSet()

    override fun toString(): String = "($level) ${super.toString()}"
}