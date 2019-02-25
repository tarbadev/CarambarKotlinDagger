package com.tarbadev.carambar.service

import com.tarbadev.carambar.annotation.AllOpen
import com.tarbadev.carambar.domain.EventList
import com.tarbadev.carambar.repository.EventListRepository
import javax.inject.Inject

@AllOpen
class EventListService @Inject constructor(val eventListRepository: EventListRepository) {
    fun add(age: Int, eventMessage: String?) {
        val eventList = getEventList()
        val events = eventList.events[age] ?: mutableListOf()

        if (eventMessage != null) {
            events.add(eventMessage)
        }

        eventList.events[age] = events

        eventListRepository.save(eventList)
    }

    fun add(age: Int) {
        add(age, null)
    }

    fun getEventList(): EventList {
        return eventListRepository.read() ?: EventList()
    }

    fun deleteEvents() {
        eventListRepository.delete()
    }
}
