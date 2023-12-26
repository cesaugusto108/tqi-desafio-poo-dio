package augusto108.ces.bootcamptracker.security.auth

import augusto108.ces.bootcamptracker.security.model.AuthenticationModel
import augusto108.ces.bootcamptracker.security.model.Token
import augusto108.ces.bootcamptracker.util.API_VERSION
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Authentication", description = "authentication with JWT")
@RequestMapping("${API_VERSION}auth/login")
interface AuthenticationOperations {

    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Authenticate user",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = Token::class))]
            )
        ]
    )
    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authenticationModel: AuthenticationModel): ResponseEntity<Token>
}