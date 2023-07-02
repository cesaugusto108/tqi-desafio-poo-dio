package augusto108.ces.bootcamptracker.security.dao

import augusto108.ces.bootcamptracker.security.model.User

interface UserDao {
    fun saveUser(user: User): Any

    fun findUserByUsername(username: String?): User
}