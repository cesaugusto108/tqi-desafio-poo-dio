package augusto108.ces.bootcamptracker.security.repositories

import org.springframework.security.core.userdetails.UserDetails

interface UserDetailsRepository {
    fun loadUserByUsername(username: String?): UserDetails
}