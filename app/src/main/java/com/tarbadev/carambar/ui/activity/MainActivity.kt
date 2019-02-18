package com.tarbadev.carambar.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.tarbadev.carambar.R
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.ui.presenter.NewCharacterPresenter
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var newCharacterPresenter: NewCharacterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstName = findViewById<TextView>(R.id.newCharacterFirstName)
        val lastName = findViewById<TextView>(R.id.newCharacterLastName)
        val sex = findViewById<TextView>(R.id.newCharacterSex)
        val origin = findViewById<TextView>(R.id.newCharacterOrigin)

        val person = getRandomPerson()

        firstName.text = person.firstName
        lastName.text = person.lastName
        sex.text = person.sex
        origin.text = person.origin
    }

    private fun getRandomPerson(): Person {
        return newCharacterPresenter.getNewCharacter()
    }
}
