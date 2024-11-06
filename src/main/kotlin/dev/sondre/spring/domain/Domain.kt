package dev.sondre.spring.domain

import java.time.OffsetDateTime

class Domain(val name: String, val datetime: OffsetDateTime) : AbstractDomain() {

    fun assertChangeableTo(new: Domain) {
        val condition = { name == new.name }
        assert(condition, "Name cannot be changed")
    }

    // More domain logic here...
}
