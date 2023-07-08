package augusto108.ces.bootcamptracker.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Name(
    @Column(name = "first_name", nullable = false, length = 20) var firstName: String = "",
    @Column(name = "middle_name", nullable = false, length = 20) var middleName: String = "",
    @Column(name = "last_name", nullable = false, length = 20) var lastName: String = ""
) {
    override fun toString(): String {
        return if (middleName == "") "$firstName $lastName" else "$firstName $middleName $lastName"
    }
}

