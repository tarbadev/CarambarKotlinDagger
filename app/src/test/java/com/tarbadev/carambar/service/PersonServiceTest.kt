package com.tarbadev.carambar.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.tarbadev.carambar.Factory
import com.tarbadev.carambar.client.PersonClientAsync
import com.tarbadev.carambar.domain.School
import com.tarbadev.carambar.repository.PersonRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PersonServiceTest {

    private lateinit var personService: PersonService
    private val personClientAsync: PersonClientAsync = mock()
    private val personRepository: PersonRepository = mock()
    private val eventListService: EventListService = mock()

    @BeforeEach
    fun setup() {
        personService = PersonService(personClientAsync, personRepository, eventListService)
    }


    @Test
    fun `getNewCharacter returns Person from Person API and adds an event`() {
        val expectedPerson = Factory.person()

        given(personClientAsync.execute()).willReturn(personClientAsync)
        given(personClientAsync.get()).willReturn(expectedPerson)
        given(personRepository.save(expectedPerson)).willReturn(expectedPerson)

        val newPerson = personService.getNewPerson()

        assertThat(newPerson.firstName).isEqualTo(expectedPerson.firstName)
        assertThat(newPerson.lastName).isEqualTo(expectedPerson.lastName)
        assertThat(newPerson.sex).isEqualTo(expectedPerson.sex)
        assertThat(newPerson.origin).isEqualTo(expectedPerson.origin)
        assertThat(newPerson.age).isEqualTo(expectedPerson.age)

        verify(personRepository).save(expectedPerson)

        val expectedEvent = """
                You just started your life!
                You're a baby boy named John Doe from United States
            """.trimIndent()
        verify(eventListService).add(expectedEvent)
    }

    @Test
    fun `incrementAge returns Person with updated age`() {
        val originalPerson = Factory.person().copy(age = 1)
        val expectedPerson = originalPerson.copy(age = 2)

        given(personRepository.read()).willReturn(originalPerson)
        given(personRepository.save(any())).willReturn(expectedPerson)

        val person = personService.incrementAge()

        assertThat(person.age).isEqualTo(expectedPerson.age)

        verify(personRepository).save(expectedPerson)
    }

    @Test
    fun `incrementAge adds a log event with the new age`() {
        val originalPerson = Factory.person().copy(age = 1)

        given(personRepository.read()).willReturn(originalPerson)

        personService.incrementAge()

        verify(eventListService).add("Age 2")
    }

    @Test
    fun `incrementAge changes school and adds a log event with the new school`() {
        val originalPerson = Factory.person().copy(age = 2, school = School.NONE)
        val expectedPerson = originalPerson.copy(age = 3, school = School.KINDERGARTEN)

        given(personRepository.read()).willReturn(originalPerson)

        personService.incrementAge()

        verify(eventListService).add("Age 3")
        verify(eventListService).add(String.format("You just started %s", School.KINDERGARTEN.displayName))
        verify(personRepository).save(expectedPerson)
    }

    @Test
    fun `incrementAge changes school and adds a log event when ending school`() {
        val originalPerson = Factory.person().copy(age = 17, school = School.HIGH_SCHOOL)
        val expectedPerson = originalPerson.copy(age = 18, school = School.NONE)

        given(personRepository.read()).willReturn(originalPerson)

        personService.incrementAge()

        verify(eventListService).add("Age 18")
        verify(eventListService).add("You finished your studies")
        verify(personRepository).save(expectedPerson)
    }

    @Test
    fun `getPerson returns a saved person`() {
        val person = Factory.person()

        given(personRepository.read()).willReturn(person)

        assertThat(personService.getPerson()).isEqualTo(person)
    }

    @Test
    fun `getPerson returns a new person if none exist`() {
        val person = Factory.person()

        given(personRepository.read()).willReturn(null)
        given(personClientAsync.execute()).willReturn(personClientAsync)
        given(personClientAsync.get()).willReturn(person)
        given(personRepository.save(person)).willReturn(person)

        assertThat(personService.getPerson()).isEqualTo(person)
    }

    @Test
    fun `removePerson calls the repository to delete current person`() {
        personService.removePerson()

        verify(personRepository).delete()
    }
}