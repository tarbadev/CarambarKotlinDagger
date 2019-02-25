package com.tarbadev.carambar.blackbox

import androidx.test.core.app.ActivityScenario
import com.google.gson.Gson
import com.tarbadev.carambar.Factory
import com.tarbadev.carambar.blackbox.view.CharacterView
import com.tarbadev.carambar.blackbox.view.EventView
import com.tarbadev.carambar.blackbox.view.HomeView
import com.tarbadev.carambar.blackbox.view.SettingsView
import com.tarbadev.carambar.domain.EventList
import com.tarbadev.carambar.domain.Sex
import com.tarbadev.carambar.repository.EventListRepository
import com.tarbadev.carambar.repository.PersonRepository
import com.tarbadev.carambar.ui.activity.MainActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SettingsBlackboxTest: BlackboxTest() {

    @Test
    fun `clicking on the end life button shows a dialog, redirects to home and generates a new person`() {
        val existingPerson = Factory.person().copy(
            firstName = "Alex",
            lastName = "Vause",
            sex = Sex.FEMALE,
            origin = "Australia",
            age = 36
        )
        val existingEventList = EventList(mutableMapOf(Pair(36, mutableListOf("Event 1", "Event 2"))))

        writeInternalFile(EventListRepository.FILENAME, Gson().toJson(existingEventList))
        writeInternalFile(PersonRepository.FILENAME, Gson().toJson(existingPerson))
        enqueueSuccessGenerationResponse()

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val personCharacteristicView = CharacterView(activity)
            personCharacteristicView.clickOnCharacterTab()

            assertThat(personCharacteristicView.getFirstName()).isEqualTo(existingPerson.firstName)
            assertThat(personCharacteristicView.getLastName()).isEqualTo(existingPerson.lastName)
            assertThat(personCharacteristicView.getSex()).isEqualTo(existingPerson.sex.gender)
            assertThat(personCharacteristicView.getOrigin()).isEqualTo(existingPerson.origin)
            assertThat(personCharacteristicView.getAge()).isEqualTo(existingPerson.age.toString())
            assertThat(personCharacteristicView.getAgeCategory()).isEqualTo(existingPerson.ageCategory.displayName)

            val settingsView = SettingsView(activity)
            settingsView.clickOnSettingsTab()
            settingsView.clickOnEndLifeButton()
            settingsView.clickOnValidEndLifeButton()

            val mainActivityView = HomeView(activity)

            val expectedEventMessage = """
                You just started your life!
                You're a baby boy named John Doe from United States
            """.trimIndent()
            val expectedEvent = EventView("Age 0", listOf(expectedEventMessage))

            val eventList = mainActivityView.getEvents()
            assertThat(eventList).hasSize(1)
            assertThat(eventList[0]).isEqualTo(expectedEvent)

            personCharacteristicView.clickOnCharacterTab()
            val person = Factory.person()

            assertThat(personCharacteristicView.getFirstName()).isEqualTo(person.firstName)
            assertThat(personCharacteristicView.getLastName()).isEqualTo(person.lastName)
            assertThat(personCharacteristicView.getSex()).isEqualTo(person.sex.gender)
            assertThat(personCharacteristicView.getOrigin()).isEqualTo(person.origin)
            assertThat(personCharacteristicView.getAge()).isEqualTo(person.age.toString())
            assertThat(personCharacteristicView.getAgeCategory()).isEqualTo(person.ageCategory.displayName)
        }
    }
}