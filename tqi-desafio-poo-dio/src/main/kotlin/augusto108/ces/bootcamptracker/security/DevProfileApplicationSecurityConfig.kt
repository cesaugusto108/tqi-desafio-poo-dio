package augusto108.ces.bootcamptracker.security

import augusto108.ces.bootcamptracker.security.model.Role
import augusto108.ces.bootcamptracker.security.model.User
import augusto108.ces.bootcamptracker.security.model.UserRole
import augusto108.ces.bootcamptracker.security.services.UserRoleService
import augusto108.ces.bootcamptracker.security.services.UserService
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.transaction.annotation.Transactional

@Configuration
@Profile("!test & !prod & dev")
@PropertySource("classpath:app_users.properties")
@Transactional
class DevProfileApplicationSecurityConfig(
    private val userService: UserService,
    private val userRoleService: UserRoleService
) : ApplicationSecurityConfig(userService, userRoleService) {
    @PersistenceContext
    override val entityManager: EntityManager? = null

    @Value("\${users.password}")
    override val userPassword: String = ""

    @Bean
    override fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.authorizeHttpRequests {
            it
                .requestMatchers(HttpMethod.GET).hasAnyRole("NORMAL", "ADMIN")
                .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH).hasRole("ADMIN")
        }.httpBasic()

        httpSecurity.csrf().disable()

        return httpSecurity.build()
    }

    @Bean
    override fun loadUsers() {
        entityManager?.createNativeQuery("delete from user_roles_tb;")?.executeUpdate()
        entityManager?.createNativeQuery("delete from user_role_tb;")?.executeUpdate()
        entityManager?.createNativeQuery("delete from user_tb;")?.executeUpdate()

        val userRole1 = UserRole(Role.ROLE_NORMAL)
        val userRole2 = UserRole(Role.ROLE_ADMIN)

        userRoleService.saveUserRole(userRole1)
        userRoleService.saveUserRole(userRole2)

        val user1 = User(username = "devnormal", password = userPassword, isActive = true)
        val user2 = User(username = "devadmin", password = userPassword, isActive = true)

        user1.userRoles.add(userRole1)
        user2.userRoles.add(userRole2)

        userService.saveUser(user1)
        userService.saveUser(user2)
    }
}