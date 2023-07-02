package augusto108.ces.bootcamptracker.security.services

import augusto108.ces.bootcamptracker.security.dao.UserRoleDao
import augusto108.ces.bootcamptracker.security.model.UserRole
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserRoleServiceImpl(private val userRoleDao: UserRoleDao) : UserRoleService {
    override fun saveUserRole(userRole: UserRole): Any = userRoleDao.saveUserRole(userRole)

    override fun findUserRoleByRole(userRole: UserRole) = userRoleDao.findUserRoleByRole(userRole)
}