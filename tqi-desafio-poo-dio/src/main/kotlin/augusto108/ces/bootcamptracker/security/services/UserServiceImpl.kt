package augusto108.ces.bootcamptracker.security.services

import augusto108.ces.bootcamptracker.security.dao.UserDao
import augusto108.ces.bootcamptracker.security.model.User
import augusto108.ces.bootcamptracker.security.repositories.UserDetailsRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(
    private val userDao: UserDao,
    private val userDetailsRepository: UserDetailsRepository
) : UserService {
    override fun saveUser(user: User): Any = userDao.saveUser(user)

    override fun findUserByUsername(username: String): User = userDao.findUserByUsername(username)

    override fun loadUserByUsername(username: String?): UserDetails = userDetailsRepository.loadUserByUsername(username)
}