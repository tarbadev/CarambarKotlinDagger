package com.tarbadev.carambar.client

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.tarbadev.carambar.Factory
import com.tarbadev.carambar.client.entity.PersonClientNameResponse
import com.tarbadev.carambar.client.entity.PersonClientResponse
import com.tarbadev.carambar.client.entity.PersonClientResultResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal


internal class PersonClientTest {
    private val restTemplate: RestTemplate = mock()
    private val personClientUrl = "https://example.com/api/"

    private lateinit var personClient: PersonClient

    @BeforeEach
    fun setup() {
        personClient = PersonClient(personClientUrl, restTemplate)
    }

    @Test
    fun generateNewPerson_callsWebService_returnsPerson() {
        val personClientResultResponse = PersonClientResultResponse(
            listOf(
                PersonClientResponse(
                    gender = "male",
                    name = PersonClientNameResponse(first = "john", last = "doe"),
                    nat = "US"
                )
            )
        )

        given(
            restTemplate.getForEntity(
                personClientUrl,
                PersonClientResultResponse::class.java
            )
        ).willReturn(ResponseEntity(personClientResultResponse, HttpStatus.OK))

        val person = Factory.person().copy(balance = BigDecimal.ZERO)

        assertThat(personClient.generateNewPerson()).isEqualTo(person)
    }
}