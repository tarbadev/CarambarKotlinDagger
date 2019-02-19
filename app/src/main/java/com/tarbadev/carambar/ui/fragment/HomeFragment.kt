package com.tarbadev.carambar.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.tarbadev.carambar.R
import com.tarbadev.carambar.service.PersonService
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class HomeFragment: BaseFragment() {
    @Inject
    lateinit var personService: PersonService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        val ageButton = findViewById<Button>(R.id.ageButton)
        ageButton.setOnClickListener {
            personService.incrementAge()
        }
    }
}