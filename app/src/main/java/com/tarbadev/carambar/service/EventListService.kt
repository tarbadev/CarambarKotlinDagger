package com.tarbadev.carambar.service

import com.tarbadev.carambar.annotation.AllOpen
import com.tarbadev.carambar.domain.EventList
import com.tarbadev.carambar.repository.EventListRepository
import javax.inject.Inject

@AllOpen
class EventListService @Inject constructor(val eventListRepository: EventListRepository) {
    fun add(event: String) {
        val eventList = getEventList()
        eventList.events.add(event)

        eventListRepository.save(eventList)
    }

    fun getEventList(): EventList {
        return eventListRepository.read() ?: EventList()
    }
}
