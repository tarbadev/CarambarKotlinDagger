package com.tarbadev.carambar.client

import android.os.AsyncTask
import com.tarbadev.carambar.annotation.AllOpen
import com.tarbadev.carambar.domain.Person
import javax.inject.Inject

@AllOpen
class PersonClientAsync @Inject constructor(private val personClient: PersonClient)
    : AsyncTask<String, Void, Person>() {

    override fun doInBackground(vararg urls: String): Person? {
        return personClient.generateNewPerson()
    }

    override fun onPostExecute(result: Person?) {
        println("result = $result")
    }
}