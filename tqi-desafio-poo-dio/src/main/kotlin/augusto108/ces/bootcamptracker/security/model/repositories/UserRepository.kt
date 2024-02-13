package augusto108.ces.bootcamptracker.security.model.repositories

import augusto108.ces.bootcamptracker.security.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {

    fun findByIdentification(identification: String): User?
}