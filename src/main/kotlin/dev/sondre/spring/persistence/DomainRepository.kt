package dev.sondre.spring.persistence

import dev.sondre.spring.exceptions.NotFound
import dev.sondre.spring.domain.ExampleDomain
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.*


@Entity
class SQLDomain(
    @Id
    val id: UUID,
    val name: String,
    val datetime: OffsetDateTime
) : SQLModel<ExampleDomain> {

    companion object : SQLModelCreator<ExampleDomain, SQLDomain> {
        override fun fromPOJO(pojo: ExampleDomain): SQLDomain {
            return SQLDomain(pojo.loadId(), pojo.name, pojo.datetime)
        }
    }

    override fun toPOJO(): ExampleDomain {
        val d = ExampleDomain(name, datetime)
        d.withId(id)
        return d
    }
}

interface DomainRepositoryInternal : JpaRepository<SQLDomain, UUID>

@Repository
class DomainRepository(private val repo: DomainRepositoryInternal) {

    fun find(id: UUID): ExampleDomain {
        val sql = repo.findByIdOrNull(id) ?: throw NotFound("Domain with id $id not found")
        return sql.toPOJO()
    }

    fun list(): List<ExampleDomain> {
        val sqls = repo.findAll()
        return sqls.map { it.toPOJO() }
    }

    fun save(domain: ExampleDomain): ExampleDomain {
        val sql = repo.save(SQLDomain.fromPOJO(domain))
        return sql.toPOJO()
    }
}

