package dev.sondre.spring.persistence

import dev.sondre.spring.exceptions.NotFound
import dev.sondre.spring.domain.Domain
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
) : SQLModel<Domain> {

    companion object : SQLModelCreator<Domain, SQLDomain> {
        override fun fromPOJO(pojo: Domain): SQLDomain {
            return SQLDomain(pojo.loadId(), pojo.name, pojo.datetime)
        }
    }

    override fun toPOJO(): Domain {
        val d = Domain(name, datetime)
        d.withId(id)
        return d
    }
}

interface DomainRepositoryInternal : JpaRepository<SQLDomain, UUID>

@Repository
class DomainRepository(private val repo: DomainRepositoryInternal) {

    fun find(id: UUID): Domain {
        val sql = repo.findByIdOrNull(id) ?: throw NotFound("Domain with id $id not found")
        return sql.toPOJO()
    }

    fun list(): List<Domain> {
        val sqls = repo.findAll()
        return sqls.map { it.toPOJO() }
    }

    fun save(domain: Domain): Domain {
        val sql = repo.save(SQLDomain.fromPOJO(domain))
        return sql.toPOJO()
    }
}

