package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.security.UserRole
import org.springframework.data.repository.CrudRepository

interface UserRoleDao : CrudRepository<UserRole, Int> {
    fun findUserRoleByRole(userRole: UserRole)
}