package dev.sondre.spring.domain

import dev.sondre.spring.exceptions.BadRequest
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.UUID



abstract class AbstractDomainModel {
    lateinit var id: UUID

    fun withId(id: UUID) {
        if (!this::id.isInitialized) {
            this.id = id
        }
    }

    fun loadId(): UUID = id

    fun initNew() {
        if (this::id.isInitialized) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Trying to override UUID")
        }
        id = UUID.randomUUID()
    }

    fun assertInput(predicate: () -> Boolean, errorMessage: String)  {
        if (!predicate()) {
            throw BadRequest(errorMessage)
        }
    }
}
