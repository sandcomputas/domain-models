package dev.sondre.spring.persistence

import dev.sondre.spring.exceptions.NotFound
import dev.sondre.spring.domain.Example
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.*


@Entity
class SQLExample(
    @Id
    val id: UUID,
    val name: String,
    val datetime: OffsetDateTime
) : SQLModel<Example> {

    companion object : SQLModelCreator<Example, SQLExample> {
        override fun fromPOJO(pojo: Example): SQLExample {
            return SQLExample(pojo.loadId(), pojo.name, pojo.datetime)
        }
    }

    override fun toPOJO(): Example {
        val e = Example(name, datetime)
        e.withId(id)
        return e
    }
}

interface ExampleRepositoryInternal : JpaRepository<SQLExample, UUID>

@Repository
class ExampleRepository(private val repo: ExampleRepositoryInternal) {

    fun find(id: UUID): Example {
        val sql = repo.findByIdOrNull(id) ?: throw NotFound("Domain with id $id not found")
        return sql.toPOJO()
    }

    fun list(): List<Example> {
        val sqls = repo.findAll()
        return sqls.map { it.toPOJO() }
    }

    fun save(domain: Example): Example {
        val sql = repo.save(SQLExample.fromPOJO(domain))
        return sql.toPOJO()
    }
}

