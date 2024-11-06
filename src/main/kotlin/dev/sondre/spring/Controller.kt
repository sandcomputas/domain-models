package dev.sondre.spring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    val repo: DomainRepository
) {
    @GetMapping
    fun list(): List<Domain> {
        return repo.list()
    }

    @PostMapping
    fun save(@RequestBody domain: Domain): Domain {
        return repo.save(domain)
    }
}