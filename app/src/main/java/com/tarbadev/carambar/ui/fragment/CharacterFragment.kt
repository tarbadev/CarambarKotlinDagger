package com.tarbadev.carambar.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tarbadev.carambar.R
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.service.PersonService
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class CharacterFragment: BaseFragment() {
    @Inject
    lateinit var personService: PersonService

    private lateinit var person: Person

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        getPerson()
        displayPerson()
    }

    private fun getPerson() {
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