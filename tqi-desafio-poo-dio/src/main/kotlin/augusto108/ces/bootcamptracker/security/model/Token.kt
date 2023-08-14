package augusto108.ces.bootcamptracker.security.model

import java.util.*

class Token(
    val username: String,
    val authenticated: Boolean,
    val token: String,
    val issuedAt: Date,
    val expirationDate: Date
)