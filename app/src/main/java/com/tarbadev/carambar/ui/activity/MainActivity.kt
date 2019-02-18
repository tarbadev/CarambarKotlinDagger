package com.tarbadev.carambar.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.tarbadev.carambar.R
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.service.PersonService
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var personService: PersonService

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstName = findViewById<TextView>(R.id.newCharacterFirstName)
        val lastName = findViewById<TextView>(R.id.newCharacterLastName)
        val sex = findViewById<TextView>(R.id.newCharacterSex)
        val origin = findViewById<TextView>(R.id.newCharacterOrigin)
        val age = findViewById<TextView>(R.id.newCharacterAge)

        val person = getRandomPerson()

        firstName.text = person.firstName
        lastName.text = person.lastName
        sex.text = person.sex
        origin.text = person.origin
        age.text = person.age.toString()
    }

    private fun getRandomPerson(): Person {
        return personService.getNewCharacter()
    }
}
