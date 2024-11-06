package dev.sondre.spring.domain

class Domain(val name: String) : AbstractDomain() {

    fun assertChangeableTo(new: Domain) {
        val condition = { name == new.name }
        assert(condition, "Name cannot be changed")
    }

    // More domain logic here...
}
