package augusto108.ces.bootcamptracker.security.services

import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails
import java.util.function.Function

interface JwtService {
    fun generateToken(claims: Map<String, Any>, userDetails: UserDetails): String

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T

    fun extractIdentification(token: String): String

    fun validateToken(token: String, userDetails: UserDetails): Boolean
}