package com.tarbadev.carambar.ui.presenter

import com.tarbadev.carambar.client.PersonClientAsync
import com.tarbadev.carambar.domain.Person
import javax.inject.Inject

class NewCharacterPresenter @Inject constructor(private val personClientAsync: PersonClientAsync) {
    fun getNewCharacter(): Person {
        return personClientAsync.execute().get()
    }
}
