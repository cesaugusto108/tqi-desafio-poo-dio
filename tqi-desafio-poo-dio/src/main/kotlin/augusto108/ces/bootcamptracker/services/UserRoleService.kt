package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.security.UserRole

interface UserRoleService {
    fun saveUserRole(userRole: UserRole): Any

    fun findUserRoleByRole(userRole: UserRole)
}