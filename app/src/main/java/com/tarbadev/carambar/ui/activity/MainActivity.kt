package com.tarbadev.carambar.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tarbadev.carambar.R
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.service.PersonService
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var personService: PersonService

    private lateinit var person: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRandomPerson()
        displayPerson()

        val ageButton = findViewById<Button>(R.id.ageButton)
        ageButton.setOnClickListener {
            person = personService.incrementAge()
            displayPerson()
        }
    }

    private fun getRandomPerson() {
        person = personService.getPerson()
    }

    private fun displayPerson() {
        val firstName = findViewById<TextView>(R.id.newCharacterFirstName)
        val lastName = findViewById<TextView>(R.id.newCharacterLastName)
        val sex = findViewById<TextView>(R.id.newCharacterSex)
        val origin = findViewById<TextView>(R.id.newCharacterOrigin)
        val age = findViewById<TextView>(R.id.newCharacterAge)
        val ageCategory = findViewById<TextView>(R.id.newCharacterAgeCategory)

        firstName.text = person.firstName
        lastName.text = person.lastName
        sex.text = person.sex
        origin.text = person.origin
        age.text = person.age.toString()
        ageCategory.text = person.ageCategory.displayName
    }
}
