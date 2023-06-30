package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.UserDao
import augusto108.ces.bootcamptracker.security.User
import augusto108.ces.bootcamptracker.security.UserRole
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(private val userDao: UserDao) : UserService {
    override fun saveUser(user: User): Any = userDao.save(user)

    override fun findUserByUsername(username: String): User = userDao.findUserByUsername(username)

    override fun loadUserByUsername(username: String?): UserDetails {
        val user: User = userDao.findUserByUsername(username)

        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            mapRolesToPermissions(user.userRoles)
        )
    }

    private fun mapRolesToPermissions(roles: Collection<UserRole>): MutableList<SimpleGrantedAuthority>? =
        roles.stream().map { SimpleGrantedAuthority(it.toString()) }.toList()
}