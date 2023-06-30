package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.security.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun saveUser(user: User): Any

    fun findUserByUsername(username: String): User
}