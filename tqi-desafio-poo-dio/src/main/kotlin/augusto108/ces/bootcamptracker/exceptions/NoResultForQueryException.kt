package augusto108.ces.bootcamptracker.exceptions

import jakarta.persistence.NoResultException

class NoResultForQueryException(val msg: String) : NoResultException() {
    override val message: String
        get() = "No result found. For query: $msg"
}