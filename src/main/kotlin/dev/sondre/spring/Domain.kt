package dev.sondre.spring

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

class Domain(
    val name: String,
) {
    // Hvis vi er ekstremt opptatt av at Swagger skal være korrekt, kan vi kan med denne for
    // å vise at bruker ikke selv kan oppdatere denne.
    // jeg stemmer i mot. Det bør være åpenbart at bruker ikke kan bestemme id
    @Schema(readOnly = true)
    lateinit var id: UUID

    fun initNew() {
        if (this::id.isInitialized) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Trying to override UUID")
        }
        id = UUID.randomUUID()
    }
}
