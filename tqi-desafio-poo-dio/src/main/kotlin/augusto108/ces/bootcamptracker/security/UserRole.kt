package augusto108.ces.bootcamptracker.security

import augusto108.ces.bootcamptracker.model.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "user_role")
class UserRole(
    @Enumerated @Column(name = "role", nullable = false, length = 20, unique = true) val role: Role,
    id: Int = 0
) : BaseEntity(id) {
    override fun toString(): String = role.toString()
}