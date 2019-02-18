package com.tarbadev.carambar.service

import com.tarbadev.carambar.client.PersonClientAsync
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.repository.PersonRepository
import javax.inject.Inject

class PersonService @Inject constructor(
    private val personClientAsync: PersonClientAsync,
    private val personRepository: PersonRepository
) {
    fun getNewCharacter(): Person {
        val person = personClientAsync.execute().get()

        return personRepository.save(person)
    }

    fun incrementAge(): Person {
        val person = personRepository.getPerson()

        return personRepository.save(person.copy(age = person.age.plus(1)))
    }
}
