# Domain Models

Playground for experimenting with ways to implement domains models.

The goal here has been to use one distinct domain model throughout the entire application. However, when using JPA it is hard to avoid adding JPA specific annotations to your models, so we have the SQLDomain model which implements the SQLModel and SQLModelCreator interfaces. This allows us to have a clear separation between persistence specific logic and business logic. 

Also, we are trying to avoid having to add repetitive validation annotations to our domain objects. Things like non-null checks should be handled by the framework by using reflection to realize what  Kotlin objects are non-nullable and therefore should not be allowed to be null in incoming requests. Adding a @NotNull annotation should be redundant. 


## Other options

If we really want Swagger to be 100% accurate, we can add one annotation:
```kotlin
class Domain(
val name: String,
) {
// Hvis vi er ekstremt opptatt av at Swagger skal være korrekt, kan vi kan med denne for
// å vise at bruker ikke selv kan oppdatere denne.
// jeg stemmer i mot. Det bør være åpenbart at bruker ikke kan bestemme id
@Schema(readOnly = true)
lateinit var id: UUID

    fun initNew() {
        if (this::id.isInitialized) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Trying to override UUID")
        }
        id = UUID.randomUUID()
    }
}
```

