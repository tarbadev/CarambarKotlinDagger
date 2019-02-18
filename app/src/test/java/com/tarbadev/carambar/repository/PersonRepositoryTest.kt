package com.tarbadev.carambar.repository

import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.domain.AgeCategory
import com.tarbadev.carambar.helper.InternalFileHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class PersonRepositoryTest {
    private lateinit var personRepository: PersonRepository

    private val internalFileHelper: InternalFileHelper = mock()

    @Before
    fun setup() {
        personRepository = PersonRepository(internalFileHelper)
    }

    @Test
    fun `save writes person file with person object`() {
        val person = Person(
            firstName = "John",
            lastName = "Doe",
            sex = "Male",
            origin = "United States",
            age = 18,
            ageCategory = AgeCategory.BABY
        )
        val expectedFileContent = Gson().toJson(person)
        val returnedPerson = personRepository.save(person)

        assertThat(returnedPerson).isEqualTo(person)

        verify(internalFileHelper).saveToFile(PersonRepository.FILENAME, expectedFileContent)
    }

    @Test
    fun `getPerson reads person file and returns Person`() {
        val person = Person(
            firstName = "John",
            lastName = "Doe",
            sex = "Male",
            origin = "United States",
            age = 18,
            ageCategory = AgeCategory.BABY
        )
        val fileContent = Gson().toJson(person)

        given(internalFileHelper.getFileContent(PersonRepository.FILENAME)).willReturn(fileContent)

        val returnedPerson = personRepository.getPerson()

        assertThat(returnedPerson).isEqualTo(person)
    }

    @Test
    fun `getPerson returns null if person not found`() {
        given(internalFileHelper.getFileContent(PersonRepository.FILENAME)).willReturn(null)

        assertThat(personRepository.getPerson()).isNull()
    }
}