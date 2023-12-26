package augusto108.ces.bootcamptracker.security.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function

@Service
class JwtServiceImpl : JwtService {

    @Value("\${security.jwt.token.secret-key}")
    val secretKey: String = ""

    @Value("\${security.jwt.token.expire-length}")
    val expirationTime: Int = 0

    override fun extractIdentification(token: String): String = extractClaim(token, Claims::getSubject)

    override fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    override fun generateToken(claims: Map<String, Any>, userDetails: UserDetails): String =
        Jwts
            .builder()
            .setClaims(claims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()

    override fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val identification: String = extractIdentification(token)
        return (identification == userDetails.username && !checkExpirationDate(token))
    }

    private fun getSigningKey(): Key {
        val key: ByteArray = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(key)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).body
    }

    private fun checkExpirationDate(token: String): Boolean {
        val expirationDate = extractExpirationDate(token)
        return expirationDate.before(Date(System.currentTimeMillis()))
    }

    private fun extractExpirationDate(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }
}