package dev.sondre.spring

import dev.sondre.spring.domain.Domain
import dev.sondre.spring.persistence.DomainRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller(private val repo: DomainRepository) {

    @GetMapping("/{id}")
    fun load(@PathVariable id: UUID): Domain {
        return repo.find(id)
    }

    @GetMapping
    fun list(): List<Domain> {
        return repo.list()
    }

    @PostMapping
    fun save(@RequestBody domain: Domain): Domain {
        domain.initNew()
        return repo.save(domain)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody new: Domain): Domain {
        new.withId(id)
        val old = repo.find(id)
        old.assertChangeableTo(new)
        return repo.save(new)
    }
}