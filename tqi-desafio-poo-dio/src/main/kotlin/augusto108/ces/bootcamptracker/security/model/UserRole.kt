package augusto108.ces.bootcamptracker.security.model

import jakarta.persistence.*

@Entity
@Table(name = "user_role_tb")
class UserRole(
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20, unique = true)
    var role: Role = Role.REGULAR,

    id: Int = 0
) : SecBaseEntity(id) {

    override fun toString(): String = role.toString()
}