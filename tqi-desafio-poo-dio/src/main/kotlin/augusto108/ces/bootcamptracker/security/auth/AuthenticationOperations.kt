package augusto108.ces.bootcamptracker.security.auth

import augusto108.ces.bootcamptracker.security.model.AuthenticationModel
import augusto108.ces.bootcamptracker.security.model.Token
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Authentication", description = "authentication with JWT")
interface AuthenticationOperations {
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