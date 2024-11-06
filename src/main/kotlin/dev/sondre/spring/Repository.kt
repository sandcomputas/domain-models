package dev.sondre.spring

import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.Id
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.ResponseStatus
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

        }
    }

    override fun toPOJO(): Domain {

    }
}


@Repository
class DomainRepository(
    @PersistenceContext val entityManager: EntityManager
) {

    fun find(id: UUID) {
        return entityManager.find()
    }

}
