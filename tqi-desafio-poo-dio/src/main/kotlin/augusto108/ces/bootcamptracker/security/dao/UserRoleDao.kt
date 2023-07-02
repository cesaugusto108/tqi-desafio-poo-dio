package augusto108.ces.bootcamptracker.security.dao

import augusto108.ces.bootcamptracker.security.model.UserRole

interface UserRoleDao {
    fun saveUserRole(userRole: UserRole): Any

    fun findUserRoleByRole(userRole: UserRole): UserRole
}