package augusto108.ces.bootcamptracker.security

import augusto108.ces.bootcamptracker.model.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_tb")
class User(
    @Column(name = "username", nullable = false, unique = true, length = 20) val username: String,
    @Column(name = "password", nullable = false) val password: String,
    @Column(name = "is_active", nullable = false) val isActive: Boolean,
    id: Int = 0
) : BaseEntity(id) {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles_tb",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val userRoles: MutableSet<UserRole> = HashSet()
}