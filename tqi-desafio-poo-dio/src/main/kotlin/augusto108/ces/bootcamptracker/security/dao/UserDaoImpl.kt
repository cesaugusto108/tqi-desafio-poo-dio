package augusto108.ces.bootcamptracker.security.dao

import augusto108.ces.bootcamptracker.security.model.User
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Component

@Component
class UserDaoImpl(private val entityManager: EntityManager) : UserDao {
    override fun saveUser(user: User): Any = entityManager.persist(user)

    override fun findUserByUsername(username: String?): User =
        entityManager
            .createQuery("from User u where username = :username", User::class.java)
            .setParameter("username", username)
            .singleResult
}