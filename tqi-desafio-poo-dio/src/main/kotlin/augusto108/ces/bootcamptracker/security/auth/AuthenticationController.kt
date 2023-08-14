package augusto108.ces.bootcamptracker.security.auth

import augusto108.ces.bootcamptracker.security.model.AuthenticationModel
import augusto108.ces.bootcamptracker.security.model.Token
import augusto108.ces.bootcamptracker.security.services.AuthenticationService
import augusto108.ces.bootcamptracker.util.API_VERSION
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${API_VERSION}auth/login")
class AuthenticationController(private val authenticationService: AuthenticationService) :
    AuthenticationOperations {
    override fun authenticate(authenticationModel: AuthenticationModel): ResponseEntity<Token> =
        ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticate(authenticationModel))
}