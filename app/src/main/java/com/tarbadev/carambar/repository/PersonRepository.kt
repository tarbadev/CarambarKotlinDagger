package com.tarbadev.carambar.repository

import com.google.gson.Gson
import com.tarbadev.carambar.annotation.AllOpen
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.helper.InternalFileHelper
import javax.inject.Inject

@AllOpen
class PersonRepository @Inject constructor(val internalFileHelper: InternalFileHelper) {
    companion object {
        const val FILENAME = "person.json"
    }

    fun getPerson(): Person {
        val content = internalFileHelper.getFileContent(FILENAME)
        return Gson().fromJson(content, Person::class.java)
    }

    fun save(person: Person): Person {
        internalFileHelper.saveToFile(FILENAME, Gson().toJson(person))

        return person
    }
}
