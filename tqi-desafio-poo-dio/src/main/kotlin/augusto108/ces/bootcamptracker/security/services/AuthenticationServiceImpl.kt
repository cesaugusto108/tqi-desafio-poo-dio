package augusto108.ces.bootcamptracker.security.services

import augusto108.ces.bootcamptracker.security.model.AuthenticationModel
import augusto108.ces.bootcamptracker.security.model.Token
import augusto108.ces.bootcamptracker.security.model.User
import augusto108.ces.bootcamptracker.security.model.repositories.UserRepository
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationServiceImpl(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) : AuthenticationService {
    override fun authenticate(authenticationModel: AuthenticationModel): Token {
        authenticationManager
            .authenticate(
                UsernamePasswordAuthenticationToken(authenticationModel.username, authenticationModel.password)
            )

        val user: User = userRepository.findByIdentification(authenticationModel.username)
            ?: throw UsernameNotFoundException("Username not found")
        val token: String = jwtService.generateToken(HashMap(), user)
        val issuedAt: Date = jwtService.extractClaim(token, Claims::getIssuedAt)
        val expirationDate: Date = jwtService.extractClaim(token, Claims::getExpiration)
        val username: String = jwtService.extractClaim(token, Claims::getSubject)
        val authenticated: Boolean = SecurityContextHolder.getContext().authentication.isAuthenticated

        return Token(
            token = token,
            issuedAt = issuedAt,
            expirationDate = expirationDate,
            username = username,
            authenticated = authenticated
        )
    }
}