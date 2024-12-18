package dev.sondre.spring.exceptions

import com.fasterxml.jackson.databind.exc.InvalidFormatException
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

    @ExceptionHandler(InvalidFormatException::class)
    fun handleInvalidFormatException(e: InvalidFormatException): ResponseEntity<String> {
        val fieldName = e.path.joinToString(".") { it.fieldName }
        val errorMessage = "$fieldName has incorrect format"
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MismatchedInputException::class)
    fun handleMismatchedInputException(e: MismatchedInputException): ResponseEntity<Map<String, String>> {
        val fieldName = e.path.joinToString(".") { it.fieldName }
        val errorMessage = "$fieldName cannot be null"
        return ResponseEntity(mapOf("error" to errorMessage), HttpStatus.BAD_REQUEST)
    }
}
