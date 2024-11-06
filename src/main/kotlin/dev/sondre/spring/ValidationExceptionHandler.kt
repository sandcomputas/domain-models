package dev.sondre.spring

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ValidationExceptionHandler {

    @ExceptionHandler(MismatchedInputException::class)
    fun handleMismatchedInputException(e: MismatchedInputException): ResponseEntity<Map<String, String>> {
        val fieldName = e.path.joinToString(".") { it.fieldName }
        val errorMessage = "$fieldName cannot be null"
        return ResponseEntity(mapOf("error" to errorMessage), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BadRequest::class)
    fun handleBadRequest(e: BadRequest): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFound::class)
    fun handleNotFound(e: NotFound): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    // Todo add custom exceptions for bad_request, etc...
}
