package augusto108.ces.bootcamptracker.handlers

import augusto108.ces.bootcamptracker.exceptions.UnmatchedIdException
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.NoResultException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.LocalDateTime
import java.util.logging.Logger

@ControllerAdvice
class ApplicationExceptionHandler {

    private val logger: Logger = Logger.getLogger(ApplicationExceptionHandler::class.java.simpleName)
    private val mediaType: MediaType = MediaType.APPLICATION_PROBLEM_JSON

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    fun handleNotAcceptable(e: HttpMediaTypeNotAcceptableException): ResponseEntity<ErrorResponse> {
        logger.info("Exception thrown: ${e.javaClass.name}")
        val errorResponse = ErrorResponse(e.message, HttpStatus.NOT_ACCEPTABLE)
        return ResponseEntity.status(406).contentType(mediaType).body(errorResponse)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class, NoResultException::class)
    fun handleNotFound(e: Exception): ResponseEntity<ErrorResponse> {
        logger.info("Exception thrown: ${e.javaClass.name}")
        val errorResponse = ErrorResponse(e.message, HttpStatus.NOT_FOUND)
        return ResponseEntity.status(404).contentType(mediaType).body(errorResponse)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException::class, UnmatchedIdException::class)
    fun handleBadRequest(e: RuntimeException): ResponseEntity<ErrorResponse> {
        logger.info("Exception thrown: ${e.javaClass.name}")
        val errorResponse = ErrorResponse(e.message, HttpStatus.BAD_REQUEST)
        return ResponseEntity.status(400).contentType(mediaType).body(errorResponse)
    }

    inner class ErrorResponse(val message: String?, val status: HttpStatus) {

        val statusCode = status.value()

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        val timestamp: LocalDateTime = LocalDateTime.now()
    }
}