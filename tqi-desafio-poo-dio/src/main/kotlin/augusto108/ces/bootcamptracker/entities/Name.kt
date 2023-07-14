package augusto108.ces.bootcamptracker.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Name(
    @Column(name = "first_name", nullable = false, length = 20) val firstName: String = "",
    @Column(name = "middle_name", nullable = false, length = 20) val middleName: String = "",
    @Column(name = "last_name", nullable = false, length = 20) val lastName: String = ""
) {
    override fun toString(): String {
        return if (middleName == "") "$firstName $lastName" else "$firstName $middleName $lastName"
    }
}

