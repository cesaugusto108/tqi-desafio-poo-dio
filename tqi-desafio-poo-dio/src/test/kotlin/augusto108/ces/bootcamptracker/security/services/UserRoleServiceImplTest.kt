package augusto108.ces.bootcamptracker.security.services

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.security.model.Role
import augusto108.ces.bootcamptracker.security.model.UserRole
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("securitytest")
class UserRoleServiceImplTest(@Autowired private val userRoleService: UserRoleService) : TestContainersConfig() {
    @PersistenceContext
    private val entityManager: EntityManager? = null

    private var userRoles: MutableList<UserRole>? = null

    @BeforeEach
    fun setUp() {
        userRoles = entityManager?.createQuery("from UserRole order by id", UserRole::class.java)?.resultList
    }

    @Test
    fun saveUserRole() {
        userRoleService.saveUserRole(UserRole(role = Role.ROLE_TEST))

        userRoles = entityManager?.createQuery("from UserRole order by id", UserRole::class.java)?.resultList

        assertEquals(3, userRoles?.size)
    }

    @Test
    fun findUserRoleByRole() {
        val userRole: UserRole? = userRoles?.get(1)

        assertEquals(Role.ROLE_ADMIN, userRole?.let { userRoleService.findUserRoleByRole(it) }?.role)
    }
}