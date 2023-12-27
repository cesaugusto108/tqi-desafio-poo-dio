package augusto108.ces.bootcamptracker.security.filter

import augusto108.ces.bootcamptracker.security.services.JwtService
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getHeader("Authorization").also {
            val prefix = "Bearer "
            val identification: String

            if ((it == null) || !it.startsWith(prefix)) {
                filterChain.doFilter(request, response)
                return
            }

            val token: String = it.substring(prefix.length)
            try {
                identification = jwtService.extractIdentification(token)
            } catch (e: Exception) {
                when (e is ExpiredJwtException || e is MalformedJwtException) {
                    true -> sendExceptionResponse(response, e)
                    else -> throw Exception()
                }
                return
            }

            authenticate(identification, token, request)
            filterChain.doFilter(request, response)
        }
    }

    private fun sendExceptionResponse(response: HttpServletResponse, e: Exception) {
        e.message?.let { ExceptionResponse("$it ") }.also {
            response.apply {
                status = HttpStatus.UNAUTHORIZED.value()
                contentType = MediaType.APPLICATION_JSON_VALUE
                writer.write(objectMapper.writeValueAsString(it))
            }
        }
    }

    private fun authenticate(identification: String, token: String, request: HttpServletRequest) {
        if (SecurityContextHolder.getContext().authentication == null) {
            userDetailsService.loadUserByUsername(identification).also {
                if (jwtService.validateToken(token, it)) {
                    UsernamePasswordAuthenticationToken(it, null, it.authorities).apply {
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = this
                    }
                }
            }
        }
    }

    inner class ExceptionResponse(val message: String) {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        val timestamp: LocalDateTime = LocalDateTime.now()
    }
}