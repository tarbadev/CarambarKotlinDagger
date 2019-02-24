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
import java.text.NumberFormat
import javax.inject.Inject


class TopBarFragment: BaseFragment() {
    @Inject
    lateinit var personService: PersonService

    private lateinit var person: Person

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_bar, container, false)
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
        displayBalance()
    }

    private fun getPerson() {
        person = personService.getPerson()
    }

    private fun displayBalance() {
        val balance = findViewById<TextView>(R.id.top_bar_balance)

        balance.text = NumberFormat.getCurrencyInstance().format(person.balance)
    }
}