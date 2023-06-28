package augusto108.ces.bootcamptracker.model

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue(value = "instructor")
class Instructor(name: Name, age: Int, email: String, id: Int) : Person(name, age, email, id)