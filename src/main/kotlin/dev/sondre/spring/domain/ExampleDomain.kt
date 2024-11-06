package dev.sondre.spring.domain

import java.time.OffsetDateTime

class ExampleDomain(val name: String, val datetime: OffsetDateTime) : AbstractDomainModel() {

    fun assertChangeableTo(new: ExampleDomain) {
        val conditions = mapOf(
            "Id cannot change" to { id == new.id },
            "Name cannot change" to { name == new.name }
        )
        conditions.forEach {
            assertInput(it.value, it.key)
        }
    }

    // More domain logic here...
}
