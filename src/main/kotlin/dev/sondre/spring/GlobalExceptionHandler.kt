package dev.sondre.spring

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MismatchedInputException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMismatchedInputException(e: MismatchedInputException): ResponseEntity<Map<String, String>> {
        val fieldName = e.path.joinToString(".") { it.fieldName }
        val errorMessage = "$fieldName cannot be null"
        return ResponseEntity(mapOf("error" to errorMessage), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnhandledException(e: Exception): ResponseEntity<String> {
        return ResponseEntity("500 - Internal Error", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
