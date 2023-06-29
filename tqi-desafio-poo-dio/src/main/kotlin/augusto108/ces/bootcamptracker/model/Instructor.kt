package augusto108.ces.bootcamptracker.model

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue(value = "instructor")
class Instructor(
    @Column(name = "developer_level", nullable = true) var level: Int? = null,
    name: Name = Name(),
    age: Int = 0,
    email: String = "",
    id: Int = 0
) : Person(name, age, email, id)