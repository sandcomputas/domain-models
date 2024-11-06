package dev.sondre.spring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    val domainDb = mutableListOf<Domain>()

    @GetMapping
    fun list(): List<Domain> {
        return domainDb
    }

    @PostMapping
    fun save(@RequestBody domain: Domain): Domain {
        domainDb.add(domain)
        return domain
    }

}