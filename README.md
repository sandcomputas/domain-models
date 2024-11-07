# Domain Models

Playground for experimenting with ways to implement domains models.

The goal here has been to use one distinct domain model throughout the entire application. However, when using JPA it is hard to avoid adding JPA specific annotations to your models, so we have the SQLDomain model which implements the SQLModel and SQLModelCreator interfaces. This allows us to have a clear separation between persistence specific logic and business logic. 

Also, we are trying to avoid having to add repetitive validation annotations to our domain objects. Things like non-null checks should be handled by the framework by using reflection to realize what  Kotlin objects are non-nullable and therefore should not be allowed to be null in incoming requests. Adding a @NotNull annotation should be redundant. 


## Følgende prinsipper er forsøkt oppnådd med dette prosjektet:

1. **En clean domene modell - klar separasjon mellom business logikk og persistence-logikk**: Så få varianter av et type domene objekt som mulig (prøver å unngå DTO, DAO etc). Så langt tror jeg vi kan klare oss med 2 varienter av en modell; en "hoved"-domene modell (Example) og en persistence spesifikk modell (SQLExample). All mapping mellom disse gjøres i database-laget, slik at database logikk ikke blandes med annen business logikk.
2. **OOP prinsipper med rike domeneobjekter ([unngå Anemic Domain Model(veldig bra artikkel)](https://martinfowler.com/bliki/AnemicDomainModel.html))** På "hoved"-modellen kan vi ha all domene logikk relatert til dette objektet, også er det servicen's jobb å komponere domene objektene til å gjøre ulike ting. Dvs. tanken er at det ikke er mye detaljert kode i servicen, men heller kall til metoder på domene objektene, på den måten blir det enklere å lese den faktiske business logikken som skjer i de ulike service metodene.
3. **Kaste exceptions så tidlig som mulig.** Så hvis vi i databaselaget prøver å hente en entitet som ikke finnes, så kan vi allerede der kaste exception. På den måten slipper vi å deale med mulig null verdier og annen feilhåndtering høyere opp i call stacken. For å håndtere dette enklest mulig har jeg opprettet noen spesifikke exception typer som BadRequest og NotFound. Ved hjelp av @ControllerAdvice exception handler metoder returnerer vi riktig feilmelding til bruker (403 og 404).
4. **Prøvd å redusere antallet annotasjoner på domene objektet.** Det kan være at det er noen tilfeller der vi må ha det, men for ting som @NotNull sjekk så kan det kanskje være bedre å ha en @ControllerAdvice exceptionhandler som fanger opp de exceptions'ene som Jackson allerede kaster når det blir sendt inn verdier til objekter som er definert i Kotlin som "non-nullable". På den måten kan vi bruke typesystemet til Kotlin for å redusere antallet annotasjoner endel i hvert fall.


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

