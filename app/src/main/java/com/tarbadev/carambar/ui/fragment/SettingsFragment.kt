package com.tarbadev.carambar.ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.tarbadev.carambar.R
import com.tarbadev.carambar.service.EventListService
import com.tarbadev.carambar.service.PersonService
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class SettingsFragment: BaseFragment() {
    @Inject
    lateinit var personService: PersonService
    @Inject
    lateinit var eventListService: EventListService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
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
        val endLifeButton = findViewById<Button>(R.id.endLifeButton)
        endLifeButton.setOnClickListener {
            AlertDialog
                .Builder(context!!)
                .setTitle(R.string.endLifeDialogTitle)
                .setMessage(R.string.endLifeDialogMessage)
                .setPositiveButton(R.string.endLifeConfirm) { _, _ ->
                    personService.removePerson()
                    eventListService.deleteEvents()
                    switchToHome()
                }
                .setNegativeButton(R.string.endLifeDiscard, null)
                .create()
                .show()
        }
    }

    private fun switchToHome() {
        fragmentManager!!
            .beginTransaction()
            .replace(R.id.main_container, HomeFragment())
            .commit()
    }
}