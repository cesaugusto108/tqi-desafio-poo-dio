package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.security.User
import org.springframework.data.repository.CrudRepository

interface UserDao : CrudRepository<User, Int> {
    fun findUserByUsername(username: String?): User
}