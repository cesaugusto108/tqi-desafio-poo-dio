package augusto108.ces.bootcamptracker.controllers.handlers

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.NoResultException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.LocalDateTime
import java.util.logging.Logger

@ControllerAdvice
class ApplicationExceptionHandler {
    private val logger: Logger = Logger.getLogger(ApplicationExceptionHandler::class.java.simpleName)

    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    fun handleNotAcceptable(e: HttpMediaTypeNotAcceptableException): ResponseEntity<ErrorResponse> {
        logger.info("Exception thrown: ${e.javaClass.name}")

        return ResponseEntity
            .status(HttpStatus.NOT_ACCEPTABLE)
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(ErrorResponse(e.message, HttpStatus.NOT_ACCEPTABLE))
    }

    @ExceptionHandler(NoHandlerFoundException::class, NoResultException::class)
    fun handleNotFound(e: Exception): ResponseEntity<ErrorResponse> {
        logger.info("Exception thrown: ${e.javaClass.name}")

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(ErrorResponse(e.message, HttpStatus.NOT_FOUND))
    }

    @ExceptionHandler(NumberFormatException::class)
    fun handleBadRequest(e: NumberFormatException): ResponseEntity<ErrorResponse> {
        logger.info("Exception thrown: ${e.javaClass.name}")

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(ErrorResponse(e.message, HttpStatus.BAD_REQUEST))
    }

    inner class ErrorResponse(
        val message: String?,
        val status: HttpStatus
    ) {
        val statusCode = status.value()

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        val timestamp: LocalDateTime = LocalDateTime.now()
    }
}