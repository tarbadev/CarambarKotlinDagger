package com.tarbadev.carambar.service

import com.tarbadev.carambar.client.PersonClientAsync
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.repository.PersonRepository
import javax.inject.Inject

class PersonService @Inject constructor(
    private val personClientAsync: PersonClientAsync,
    private val personRepository: PersonRepository,
    private val eventListService: EventListService
) {
    private companion object {
        val EVENT_FORMAT = """
                You just started your life!
                You're a %s %s named %s %s from %s
            """.trimIndent()
    }

    fun getNewPerson(): Person {
        val person = personClientAsync.execute().get()

        val event = String.format(
            EVENT_FORMAT,
            person.ageCategory.displayName.toLowerCase(),
            person.sex.genderChild.toLowerCase(),
            person.firstName,
            person.lastName,
            person.origin
        )
        eventListService.add(event)

        return personRepository.save(person)
    }

    fun incrementAge(): Person {
        val person = personRepository.read()

        return personRepository.save(person!!.copy(age = person.age.plus(1)))
    }

    fun getPerson(): Person {
        val person = personRepository.read()
        return person ?: getNewPerson()
    }
}
