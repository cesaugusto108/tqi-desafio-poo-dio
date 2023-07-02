package augusto108.ces.bootcamptracker.security.services

import augusto108.ces.bootcamptracker.security.model.User
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("securitytest")
class UserServiceImplTest(@Autowired private val userService: UserService) {
    @PersistenceContext
    private val entityManager: EntityManager? = null

    @Test
    fun saveUser() {
        userService.saveUser(User(username = "", password = "", isActive = true))

        val users = entityManager?.createQuery("from User order by id", User::class.java)?.resultList

        assertEquals(3, users?.size)
    }

    @Test
    fun findUserByUsername() {
        assertEquals("normal", userService.findUserByUsername("normal").username)
    }

    @Test
    fun loadUserByUsername() {
        assertEquals("admin", userService.loadUserByUsername("admin").username)
    }
}