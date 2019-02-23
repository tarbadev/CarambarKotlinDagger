package com.tarbadev.carambar.service

import com.tarbadev.carambar.client.PersonClientAsync
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.domain.School
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
        val originalSchool = person!!.school
        val newAge = person.age.plus(1)
        val newSchool = getSchoolForAge(newAge)
        val updatedPerson = person.copy(age = newAge, school = newSchool)

        eventListService.add(String.format("Age %d", updatedPerson.age))

        if (originalSchool != newSchool) {
            var message = String.format("You just started %s", newSchool.displayName)

            if (newSchool == School.NONE) {
                message = "You finished your studies"
            }

            eventListService.add(message)
        }

        return personRepository.save(updatedPerson)
    }

    fun getPerson(): Person {
        val person = personRepository.read()
        return person ?: getNewPerson()
    }

    fun removePerson() {
        personRepository.delete()
    }

    private fun getSchoolForAge(age: Int): School {
        return when (age) {
            in 15..17 -> School.HIGH_SCHOOL
            in 11..14 -> School.MIDDLE_SCHOOL
            in 6..10 -> School.PRIMARY_SCHOOL
            in 3..5 -> School.KINDERGARTEN
            else -> School.NONE
        }
    }
}
