package dev.sondre.spring.domain

import java.time.OffsetDateTime

class Domain(val name: String, val datetime: OffsetDateTime) : AbstractDomain() {

    fun assertChangeableTo(new: Domain) {
        val conditions = mapOf(
            "Id cannot change" to { id == new.id },
            "Name cannot change" to { name == new.name }
        )
        conditions.forEach {
            assert(it.value, it.key)
        }
    }

    // More domain logic here...
}
