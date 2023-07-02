package augusto108.ces.bootcamptracker.security.repositories

import org.springframework.security.core.userdetails.UserDetails

interface UserRepository {
    fun loadUserByUsername(username: String?): UserDetails
}