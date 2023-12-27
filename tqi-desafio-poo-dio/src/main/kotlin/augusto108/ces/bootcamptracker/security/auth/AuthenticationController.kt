package augusto108.ces.bootcamptracker.security.auth

import augusto108.ces.bootcamptracker.security.model.AuthenticationModel
import augusto108.ces.bootcamptracker.security.model.Token
import augusto108.ces.bootcamptracker.security.services.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(private val authenticationService: AuthenticationService) : AuthenticationOperations {

    override fun authenticate(authenticationModel: AuthenticationModel): ResponseEntity<Token> {
        authenticationService.authenticate(authenticationModel).also {
            return ResponseEntity.status(200).body(it)
        }
    }
}