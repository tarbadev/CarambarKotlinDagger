package com.tarbadev.carambar.blackbox

import androidx.test.core.app.ActivityScenario
import com.google.gson.Gson
import com.tarbadev.carambar.Factory.Companion.person
import com.tarbadev.carambar.blackbox.view.HomeView
import com.tarbadev.carambar.blackbox.view.CharacterView
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
            assertThat(eventList[eventListSize]).isEqualTo("Age 1")
            assertThat(eventList[eventListSize + 1]).isEqualTo("Age 2")

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

            assertThat(eventList[0]).isEqualTo(expectedEvent)
        }
    }

    @Test
    fun `home displays a list of events`() {
        val event1 = "Age 12"
        val event2 = """
            Some
            Multiline
            Event
        """.trimIndent()
        val eventListJson = Gson().toJson(EventList(mutableListOf(event1, event2)))

        writeInternalFile(EventListRepository.FILENAME, eventListJson)
        writeInternalFile(PersonRepository.FILENAME, Gson().toJson(person()))

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)

            val eventList = mainActivityView.getEvents()
            assertThat(eventList).hasSize(2)

            assertThat(eventList[0]).isEqualTo(event1)
            assertThat(eventList[1]).isEqualTo(event2)
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
            assertThat(eventList[eventList.size - 1]).isEqualTo(
                String.format(
                    "You just started %s",
                    School.KINDERGARTEN.displayName
                )
            )

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            mainActivityView.clickOnCharacterTab()
            assertThat(personCharacteristicView.getAge()).isEqualTo("6")
            assertThat(personCharacteristicView.getSchool()).isEqualTo("Primary School")

            mainActivityView.clickOnHomeTab()
            eventList = mainActivityView.getEvents()
            assertThat(eventList[eventList.size - 1]).isEqualTo(
                String.format(
                    "You just started %s",
                    School.PRIMARY_SCHOOL.displayName
                )
            )

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
            assertThat(eventList[eventList.size - 1]).isEqualTo(
                String.format(
                    "You just started %s",
                    School.MIDDLE_SCHOOL.displayName
                )
            )

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            mainActivityView.clickOnCharacterTab()
            assertThat(personCharacteristicView.getAge()).isEqualTo("15")
            assertThat(personCharacteristicView.getSchool()).isEqualTo("High School")

            mainActivityView.clickOnHomeTab()
            eventList = mainActivityView.getEvents()
            assertThat(eventList[eventList.size - 1]).isEqualTo(
                String.format(
                    "You just started %s",
                    School.HIGH_SCHOOL.displayName
                )
            )

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            mainActivityView.clickOnCharacterTab()
            assertThat(personCharacteristicView.getAge()).isEqualTo("18")
            assertThat(personCharacteristicView.getSchool()).isEqualTo("N/A")

            mainActivityView.clickOnHomeTab()
            eventList = mainActivityView.getEvents()
            assertThat(eventList[eventList.size - 1]).isEqualTo(String.format("You finished your studies"))
        }
    }

    @Test
    fun `aging changes the school and gives graduate`() {
        val person = person().copy(age = 14, school = School.MIDDLE_SCHOOL)
        writeInternalFile(PersonRepository.FILENAME, Gson().toJson(person))

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)
            mainActivityView.clickOnAgeButton()

            var eventList = mainActivityView.getEvents()
            assertThat(eventList).containsSequence(
                String.format("You graduated from %s", School.MIDDLE_SCHOOL.displayName),
                String.format("You just started %s", School.HIGH_SCHOOL.displayName)
            )

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            eventList = mainActivityView.getEvents()
            assertThat(eventList).containsSequence(
                String.format("You graduated from %s", School.HIGH_SCHOOL.displayName),
                "You finished your studies"
            )
        }
    }
}