package dev.sondre.spring

import dev.sondre.spring.domain.ExampleDomain
import dev.sondre.spring.persistence.DomainRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller(private val repo: DomainRepository) {

    @GetMapping("/{id}")
    fun load(@PathVariable id: UUID): ExampleDomain {
        return repo.find(id)
    }

    @GetMapping
    fun list(): List<ExampleDomain> {
        return repo.list()
    }

    @PostMapping
    fun save(@RequestBody domain: ExampleDomain): ExampleDomain {
        domain.initNew()
        return repo.save(domain)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody new: ExampleDomain): ExampleDomain {
        new.withId(id)
        val old = repo.find(id)
        old.assertChangeableTo(new)
        return repo.save(new)
    }
}