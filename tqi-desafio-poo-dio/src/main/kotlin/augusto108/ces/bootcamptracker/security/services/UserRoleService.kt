package augusto108.ces.bootcamptracker.security.services

import augusto108.ces.bootcamptracker.security.model.UserRole

interface UserRoleService {
    fun saveUserRole(userRole: UserRole): Any

    fun findUserRoleByRole(userRole: UserRole): UserRole
}