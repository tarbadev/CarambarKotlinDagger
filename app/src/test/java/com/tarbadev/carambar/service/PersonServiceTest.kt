package com.tarbadev.carambar.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.tarbadev.carambar.client.PersonClientAsync
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.repository.PersonRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PersonServiceTest {

    private lateinit var personService: PersonService
    private val personClientAsync: PersonClientAsync = mock()
    private val personRepository: PersonRepository = mock()

    @BeforeEach
    fun setup() {
        personService = PersonService(personClientAsync, personRepository)
    }

    @Test
    fun `getNewCharacter returns Person from Person API`() {
        val expectedPerson = Person(
            firstName = "John",
            lastName = "Doe",
            sex = "Male",
            origin = "USA",
            age = 0
        )

        given(personClientAsync.execute()).willReturn(personClientAsync)
        given(personClientAsync.get()).willReturn(expectedPerson)
        given(personRepository.save(expectedPerson)).willReturn(expectedPerson)

        val newPerson = personService.getNewCharacter()

        assertThat(newPerson.firstName).isEqualTo(expectedPerson.firstName)
        assertThat(newPerson.lastName).isEqualTo(expectedPerson.lastName)
        assertThat(newPerson.sex).isEqualTo(expectedPerson.sex)
        assertThat(newPerson.origin).isEqualTo(expectedPerson.origin)
        assertThat(newPerson.age).isEqualTo(expectedPerson.age)

        verify(personRepository).save(expectedPerson)
    }

    @Test
    fun `incrementAge returns Person with updated age`() {
        val originalPerson = Person(
            firstName = "John",
            lastName = "Doe",
            sex = "Male",
            origin = "USA",
            age = 1
        )

        val expectedPerson = originalPerson.copy(age = 2)

        given(personRepository.getPerson()).willReturn(originalPerson)
        given(personRepository.save(any())).willReturn(expectedPerson)

        val person = personService.incrementAge()

        assertThat(person.age).isEqualTo(expectedPerson.age)

        verify(personRepository).save(expectedPerson)
    }
}