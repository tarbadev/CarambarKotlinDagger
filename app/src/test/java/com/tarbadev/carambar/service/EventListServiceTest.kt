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
    fun `add saves the given event even if there is no message`() {
        val eventList = EventList(events = mutableMapOf(Pair(12, mutableListOf("Original Event"))))

        val updatedEvents = eventList.copy()
        updatedEvents.events[13] = mutableListOf()

        given(eventListRepository.read()).willReturn(eventList)

        eventListService.add(13)

        verify(eventListRepository).save(updatedEvents)
    }

    @Test
    fun `add saves the given event`() {
        val event = "Event to save"
        val eventList = EventList(events = mutableMapOf(Pair(12, mutableListOf("Original Event"))))

        val updatedEvents = eventList.copy()
        updatedEvents.events[13] = mutableListOf(event)

        given(eventListRepository.read()).willReturn(eventList)

        eventListService.add(13, event)

        verify(eventListRepository).save(updatedEvents)
    }

    @Test
    fun `getEventList returns EventList`() {
        val eventList = EventList(events = mutableMapOf(Pair(12, mutableListOf("Some Event"))))

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