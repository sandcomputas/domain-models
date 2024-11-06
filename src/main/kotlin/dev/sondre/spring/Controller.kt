package dev.sondre.spring

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller(
    val repo: DomainRepository
) {

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
    fun update(@PathVariable id: UUID, @RequestBody domain: Domain): Domain {
        TODO("")
    }
}