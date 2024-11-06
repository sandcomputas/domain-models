package dev.sondre.spring

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

class Domain(
    val name: String,
) {
    lateinit var id: UUID

    fun initNew() {
        if (this::id.isInitialized) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Trying to override UUID")
        }
        id = UUID.randomUUID()
    }
}
