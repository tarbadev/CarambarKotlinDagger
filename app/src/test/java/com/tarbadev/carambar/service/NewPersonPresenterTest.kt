package com.tarbadev.carambar.service

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.tarbadev.carambar.client.PersonClientAsync
import com.tarbadev.carambar.domain.Person
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class NewPersonPresenterTest {

    private lateinit var personService: PersonService
    private val personClientAsync: PersonClientAsync = mock()

    @BeforeEach
    fun setup() {
        personService = PersonService(personClientAsync)
    }

    @Test
    fun getNewCharacter_returnsPersonFromPersonApi() {
        val expectedPerson = Person(
            firstName = "John",
            lastName = "Doe",
            sex = "Male",
            origin = "USA",
            age = 0
        )

        given(personClientAsync.execute()).willReturn(personClientAsync)
        given(personClientAsync.get()).willReturn(expectedPerson)

        val newPerson = personService.getNewCharacter()

        assertThat(newPerson.firstName).isEqualTo(expectedPerson.firstName)
        assertThat(newPerson.lastName).isEqualTo(expectedPerson.lastName)
        assertThat(newPerson.sex).isEqualTo(expectedPerson.sex)
        assertThat(newPerson.origin).isEqualTo(expectedPerson.origin)
        assertThat(newPerson.age).isEqualTo(expectedPerson.age)
    }
}