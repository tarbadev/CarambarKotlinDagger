package com.tarbadev.carambar.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.tarbadev.carambar.R
import com.tarbadev.carambar.service.EventListService
import com.tarbadev.carambar.service.PersonService
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    @Inject
    lateinit var personService: PersonService
    @Inject
    lateinit var eventListService: EventListService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personService.getPerson()
        setupListeners()
        displayEvents()
    }

    private fun displayEvents() {
        val eventList = eventListService.getEventList()
        val eventContainer = findViewById<LinearLayout>(R.id.event_list)
        val scrollView = findViewById<ScrollView>(R.id.scrollable_events)

        eventContainer.removeAllViews()

        eventList.events.forEach {
            val textView = TextView(context)
            textView.text = it
            textView.typeface = ResourcesCompat.getFont(context!!, R.font.indie_flower)

            eventContainer.addView(textView)
        }

        scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun setupListeners() {
        val ageButton = findViewById<Button>(R.id.ageButton)
        ageButton.setOnClickListener {
            personService.incrementAge()
            displayEvents()
        }
    }
}