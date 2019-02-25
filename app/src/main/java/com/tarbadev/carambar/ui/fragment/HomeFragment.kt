package com.tarbadev.carambar.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarbadev.carambar.R
import com.tarbadev.carambar.service.EventListService
import com.tarbadev.carambar.service.PersonService
import com.tarbadev.carambar.ui.adapter.EventListAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class HomeFragment : BaseFragment() {
    @Inject
    lateinit var personService: PersonService
    @Inject
    lateinit var eventListService: EventListService

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventListAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

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

        viewManager = LinearLayoutManager(context)
        eventListAdapter = EventListAdapter(eventList, context!!)

        recyclerView = findViewById<RecyclerView>(R.id.event_list_container).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = eventListAdapter
        }
        recyclerView.scrollToPosition(eventList.events.size - 1)
    }

    private fun setupListeners() {
        val ageButton = findViewById<Button>(R.id.ageButton)
        ageButton.setOnClickListener {
            personService.incrementAge()
            displayEvents()
        }
    }
}