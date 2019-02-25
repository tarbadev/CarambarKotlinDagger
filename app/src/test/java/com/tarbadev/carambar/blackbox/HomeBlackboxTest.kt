package com.tarbadev.carambar.blackbox

import androidx.test.core.app.ActivityScenario
import com.google.gson.Gson
import com.tarbadev.carambar.Factory.Companion.person
import com.tarbadev.carambar.blackbox.view.HomeView
import com.tarbadev.carambar.blackbox.view.CharacterView
import com.tarbadev.carambar.blackbox.view.EventView
import com.tarbadev.carambar.domain.EventList
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.domain.School
import com.tarbadev.carambar.domain.Sex
import com.tarbadev.carambar.repository.EventListRepository
import com.tarbadev.carambar.repository.PersonRepository
import com.tarbadev.carambar.ui.activity.MainActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class HomeBlackboxTest : BlackboxTest() {

    @Test
    fun `home displays a button Age that updates the age of the character and adds an event`() {
        enqueueSuccessGenerationResponse()

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)
            val personCharacteristicView = CharacterView(activity)

            mainActivityView.clickOnCharacterTab()

            assertThat(personCharacteristicView.getAge()).isEqualTo("0")

            mainActivityView.clickOnHomeTab()

            val eventListSize = mainActivityView.getEvents().size

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            val eventList = mainActivityView.getEvents()
            assertThat(eventList).hasSize(eventListSize + 2)
            assertThat(eventList[eventListSize].age).isEqualTo("Age 1")
            assertThat(eventList[eventListSize + 1].age).isEqualTo("Age 2")

            mainActivityView.clickOnCharacterTab()

            assertThat(personCharacteristicView.getAge()).isEqualTo("2")
        }
    }

    @Test
    fun `home generates a new character on start and displays log about it`() {
        enqueueSuccessGenerationResponse()

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)
            val expectedEvent = """
                You just started your life!
                You're a baby boy named John Doe from United States
            """.trimIndent()

            val eventList = mainActivityView.getEvents()
            assertThat(eventList).hasSize(1)

            assertThat(eventList[0].age).isEqualTo("Age 0")
            assertThat(eventList[0].events).isEqualTo(listOf(expectedEvent))
        }
    }

    @Test
    fun `home displays a list of events`() {
        val ageMessage = "Age 0"
        val eventMessage = """
            Some
            Multiline
            Event
        """.trimIndent()
        val eventListJson = Gson().toJson(EventList(mutableMapOf(Pair(0, mutableListOf(eventMessage)))))

        writeInternalFile(EventListRepository.FILENAME, eventListJson)
        writeInternalFile(PersonRepository.FILENAME, Gson().toJson(person()))

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)

            val eventList = mainActivityView.getEvents()
            assertThat(eventList).hasSize(1)

            assertThat(eventList[0].age).isEqualTo(ageMessage)
            assertThat(eventList[0].events).isEqualTo(listOf(eventMessage))
        }
    }

    @Test
    fun `home loads my previous game`() {
        val person = Person(
            firstName = "Leandro",
            lastName = "Faure",
            sex = Sex.MALE,
            origin = "Italy",
            age = 42
        )
        writeInternalFile(PersonRepository.FILENAME, Gson().toJson(person))

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)
            val personCharacteristicView = CharacterView(activity)

            mainActivityView.clickOnCharacterTab()

            assertThat(personCharacteristicView.getFirstName()).isEqualTo(person.firstName)
            assertThat(personCharacteristicView.getLastName()).isEqualTo(person.lastName)
            assertThat(personCharacteristicView.getSex()).isEqualTo(person.sex.gender)
            assertThat(personCharacteristicView.getOrigin()).isEqualTo(person.origin)
            assertThat(personCharacteristicView.getAge()).isEqualTo(person.age.toString())
        }
    }

    @Test
    fun `aging changes the school and logs in events`() {
        val person = person().copy(age = 2)
        val eventListJson = Gson().toJson(EventList(mutableMapOf(
            Pair(0, mutableListOf()),
            Pair(1, mutableListOf()),
            Pair(2, mutableListOf())
        )))
        writeInternalFile(EventListRepository.FILENAME, eventListJson)
        writeInternalFile(PersonRepository.FILENAME, Gson().toJson(person))

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)
            val personCharacteristicView = CharacterView(activity)

            mainActivityView.clickOnCharacterTab()
            assertThat(personCharacteristicView.getSchool()).isEqualTo("N/A")

            mainActivityView.clickOnHomeTab()
            mainActivityView.clickOnAgeButton()

            mainActivityView.clickOnCharacterTab()
            assertThat(personCharacteristicView.getAge()).isEqualTo("3")
            assertThat(personCharacteristicView.getSchool()).isEqualTo("Kindergarten")

            mainActivityView.clickOnHomeTab()
            var eventList = mainActivityView.getEvents()
            var expectedEvent = EventView(
                age = "Age 3",
                events = mutableListOf(String.format("You just started %s", School.KINDERGARTEN.displayName))
            )
            assertThat(eventList.last()).isEqualTo(expectedEvent)

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            mainActivityView.clickOnCharacterTab()
            assertThat(personCharacteristicView.getAge()).isEqualTo("6")
            assertThat(personCharacteristicView.getSchool()).isEqualTo("Primary School")

            mainActivityView.clickOnHomeTab()
            eventList = mainActivityView.getEvents()
            expectedEvent = EventView(
                age = "Age 6",
                events = mutableListOf(String.format("You just started %s", School.PRIMARY_SCHOOL.displayName))
            )
            assertThat(eventList.last()).isEqualTo(expectedEvent)

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            mainActivityView.clickOnCharacterTab()
            assertThat(personCharacteristicView.getAge()).isEqualTo("11")
            assertThat(personCharacteristicView.getSchool()).isEqualTo("Middle School")

            mainActivityView.clickOnHomeTab()
            eventList = mainActivityView.getEvents()
            expectedEvent = EventView(
                age = "Age 11",
                events = mutableListOf(String.format("You just started %s", School.MIDDLE_SCHOOL.displayName))
            )
            assertThat(eventList.last()).isEqualTo(expectedEvent)

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            mainActivityView.clickOnCharacterTab()
            assertThat(personCharacteristicView.getAge()).isEqualTo("15")
            assertThat(personCharacteristicView.getSchool()).isEqualTo("High School")

            mainActivityView.clickOnHomeTab()
            eventList = mainActivityView.getEvents()
            expectedEvent = EventView(
                age = "Age 15",
                events = mutableListOf(
                    String.format("You graduated from %s", School.MIDDLE_SCHOOL.displayName),
                    String.format("You just started %s", School.HIGH_SCHOOL.displayName)
                )
            )
            assertThat(eventList.last()).isEqualTo(expectedEvent)

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            mainActivityView.clickOnCharacterTab()
            assertThat(personCharacteristicView.getAge()).isEqualTo("18")
            assertThat(personCharacteristicView.getSchool()).isEqualTo("N/A")

            mainActivityView.clickOnHomeTab()
            eventList = mainActivityView.getEvents()
            expectedEvent = EventView(
                age = "Age 18",
                events = mutableListOf(
                    String.format("You graduated from %s", School.HIGH_SCHOOL.displayName),
                    "You finished your studies"
                )
            )
            assertThat(eventList.last()).isEqualTo(expectedEvent)
        }
    }
}