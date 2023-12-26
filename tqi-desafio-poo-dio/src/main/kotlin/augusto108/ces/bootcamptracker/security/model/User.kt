package augusto108.ces.bootcamptracker.security.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user_tb")
class User(
    @Column(name = "first_name", nullable = false, length = 80) var firstName: String = "",
    @Column(name = "last_name", nullable = false, length = 80) var lastName: String = "",
    @Column(name = "username", nullable = false, unique = true, length = 120) var identification: String = "",
    @Column(name = "password", nullable = false) var pass: String = "",
    @Column(name = "account_non_expired") var accountNonExpired: Boolean = false,
    @Column(name = "account_non_locked") var accountNonLocked: Boolean = false,
    @Column(name = "credentials_non_expired") var credentialsNonExpired: Boolean = false,
    @Column(name = "enabled") var enabled: Boolean = false,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles_tb",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val userRoles: MutableSet<UserRole> = HashSet(),

    id: Int = 0
) : SecBaseEntity(id), UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities: MutableList<SimpleGrantedAuthority> = arrayListOf()
        userRoles.forEach { authorities.add(SimpleGrantedAuthority(it.toString())) }
        return authorities
    }

    override fun getPassword(): String = pass

    override fun getUsername(): String = identification

    override fun isAccountNonExpired(): Boolean = accountNonExpired

    override fun isAccountNonLocked(): Boolean = accountNonLocked

    override fun isCredentialsNonExpired(): Boolean = credentialsNonExpired

    override fun isEnabled(): Boolean = enabled
}