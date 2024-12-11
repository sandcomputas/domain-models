package dev.sondre.spring

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.sondre.spring.domain.Example
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class ExampleTest(@Autowired val mockMvc: MockMvc) {

    val mapper = jacksonObjectMapper()

    @BeforeEach
    fun setup() {
        RestAssuredMockMvc.mockMvc(mockMvc)
    }

    @AfterEach
    fun teardown() {
        RestAssuredMockMvc.reset()
    }

    @Test
    fun `can load all`() {
        val response =
            RestAssuredMockMvc.`when`()
                .get("/")
                .then()
                .status(HttpStatus.OK)
                .extract()
                .response()
                .mvcResult() // TODO: create extension function that simplifies this a bit
                .response
                .contentAsString
        val list: List<Example> = mapper.readValue(response)

        println()
    }

    @Test fun `can load specific example instance from database`() {}
}
