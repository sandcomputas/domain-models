package dev.sondre.spring.persistence

import dev.sondre.spring.Domain
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.Id
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Entity
class SQLDomain(
    @Id
    val id: UUID,
    val name: String
) : SQLModel<Domain> {

    companion object : SQLModelCreator<Domain, SQLDomain> {
        override fun fromPOJO(pojo: Domain): SQLDomain {
            return SQLDomain(pojo.id, pojo.name)
        }
    }

    override fun toPOJO(): Domain {
        val d = Domain(name)
        // minor disadvantage is that id cannot be private for this to work. Another option is to have a
        // fromSQL method on all domain objects, but this is not so nice either because POJOs should maybe
        // not have to deal with persistence stuff.
        d.id = id
        return d
    }
}

interface DomainRepositoryInternal : JpaRepository<SQLDomain, UUID>

@Repository
class DomainRepository(
    @PersistenceContext val entityManager: EntityManager,
    val repo: DomainRepositoryInternal
) {

    fun find(id: UUID): Domain {
        val sql = repo.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "")
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

