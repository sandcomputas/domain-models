package dev.sondre.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class Application // not sure why this needs to be open, fix it!

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
