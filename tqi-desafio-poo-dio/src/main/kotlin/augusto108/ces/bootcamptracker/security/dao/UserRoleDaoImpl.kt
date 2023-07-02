package augusto108.ces.bootcamptracker.security.dao

import augusto108.ces.bootcamptracker.security.model.UserRole
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Component

@Component
class UserRoleDaoImpl(private val entityManager: EntityManager) : UserRoleDao {
    override fun saveUserRole(userRole: UserRole): Any = entityManager.persist(userRole)

    override fun findUserRoleByRole(userRole: UserRole): UserRole =
        entityManager
            .createQuery("from UserRole u where role = :role", UserRole::class.java)
            .setParameter("role", userRole.role)
            .singleResult
}