package augusto108.ces.bootcamptracker.security.services

import augusto108.ces.bootcamptracker.security.model.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun saveUser(user: User): Any

    fun findUserByUsername(username: String): User
}