package augusto108.ces.bootcamptracker.security.repositories

import augusto108.ces.bootcamptracker.security.dao.UserDao
import augusto108.ces.bootcamptracker.security.model.User
import augusto108.ces.bootcamptracker.security.model.UserRole
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
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