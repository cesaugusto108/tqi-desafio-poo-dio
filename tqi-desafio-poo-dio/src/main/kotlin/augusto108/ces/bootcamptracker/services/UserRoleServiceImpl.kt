package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.UserRoleDao
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserRoleServiceImpl(private val userRoleDao: UserRoleDao) : UserRoleService {
    override fun saveUserRole(userRole: augusto108.ces.bootcamptracker.security.UserRole): Any =
        userRoleDao.save(userRole)

    override fun findUserRoleByRole(userRole: augusto108.ces.bootcamptracker.security.UserRole) =
        userRoleDao.findUserRoleByRole(userRole)
}