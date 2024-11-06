package dev.sondre.spring

import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.Id
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*


interface SQLModel<POJO> {
    fun toPOJO(): POJO
}

interface SQLModelCreator<POJO, SQLModel> {
    fun fromPOJO(pojo: POJO): SQLModel
}

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
        return Domain(id, name)
    }
}


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

interface DomainRepositoryInternal : JpaRepository<SQLDomain, UUID>