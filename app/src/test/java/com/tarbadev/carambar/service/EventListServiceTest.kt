package com.tarbadev.carambar.service

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.tarbadev.carambar.domain.EventList
import com.tarbadev.carambar.repository.EventListRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EventListServiceTest {
    private lateinit var eventListService: EventListService

    private val eventListRepository: EventListRepository = mock()

    @BeforeEach
    fun setup() {
        eventListService = EventListService(eventListRepository)
    }

    @Test
    fun `add saves the given event`() {
        val event = "Event to save"
        val list = mutableListOf("Original Event")
        val eventList = EventList(events = list)

        list.add(event)
        val updatedEvents = EventList(events = list)

        given(eventListRepository.read()).willReturn(eventList)

        eventListService.add(event)

        verify(eventListRepository).save(updatedEvents)
    }

    @Test
    fun `getEventList returns EventList`() {
        val eventList = EventList(mutableListOf("Some Event"))

        given(eventListRepository.read()).willReturn(eventList)

        assertThat(eventListService.getEventList()).isEqualTo(eventList)
    }

    @Test
    fun `getEventList returns a new EventList if none exist`() {
        val eventList = EventList()

        given(eventListRepository.read()).willReturn(null)

        assertThat(eventListService.getEventList()).isEqualTo(eventList)
    }

    @Test
    fun `deleteEvents calls the repository to delete current events`() {
        eventListService.deleteEvents()

        verify(eventListRepository).delete()
    }
}