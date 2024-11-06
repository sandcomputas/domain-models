package dev.sondre.spring

import dev.sondre.spring.domain.Example
import dev.sondre.spring.persistence.ExampleRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller(private val repo: ExampleRepository) {

    @GetMapping("/{id}")
    fun load(@PathVariable id: UUID): Example {
        return repo.find(id)
    }

    @GetMapping
    fun list(): List<Example> {
        return repo.list()
    }

    @PostMapping
    fun save(@RequestBody example: Example): Example {
        example.initNew()
        return repo.save(example)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody new: Example): Example {
        new.withId(id)
        val old = repo.find(id)
        old.assertChangeableTo(new)
        return repo.save(new)
    }
}
