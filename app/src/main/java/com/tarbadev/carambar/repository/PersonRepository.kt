package com.tarbadev.carambar.repository

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.tarbadev.carambar.annotation.AllOpen
import com.tarbadev.carambar.domain.Person
import javax.inject.Inject
import javax.inject.Named

@AllOpen
class PersonRepository @Inject constructor(
    @Named("ApplicationContext") val applicationContext: Context
)
    : InternalFileRepository<Person>(FILENAME, applicationContext, object: TypeToken<Person>() {}.type) {
    companion object {
        const val FILENAME = "person.json"
    }
}
