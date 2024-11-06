package dev.sondre.spring.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class UnhandledExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleUnhandledException(e: Exception): ResponseEntity<String> {
        return ResponseEntity("Internal Error :'(", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
