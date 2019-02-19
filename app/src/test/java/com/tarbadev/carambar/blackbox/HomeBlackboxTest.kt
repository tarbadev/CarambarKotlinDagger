package com.tarbadev.carambar.blackbox

import androidx.test.core.app.ActivityScenario
import com.google.gson.Gson
import com.tarbadev.carambar.blackbox.view.HomeView
import com.tarbadev.carambar.blackbox.view.CharacterView
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.repository.PersonRepository
import com.tarbadev.carambar.ui.activity.MainActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class HomeBlackboxTest: BlackboxTest() {

    @Test
    fun `home displays a button Age that updates the age of the character`() {
        enqueueSuccessGenerationResponse()

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)
            val personCharacteristicView = CharacterView(activity)

            mainActivityView.clickOnCharacterButton()

            assertThat(personCharacteristicView.getAge()).isEqualTo("0")

            mainActivityView.clickOnHomeButton()

            mainActivityView.clickOnAgeButton()
            mainActivityView.clickOnAgeButton()

            mainActivityView.clickOnCharacterButton()

            assertThat(personCharacteristicView.getAge()).isEqualTo("2")
        }
    }

    @Test
    fun `home loads my previous game`() {
        val person = Person(
            firstName = "Leandro",
            lastName = "Faure",
            sex = "Male",
            origin = "Italy",
            age = 42
        )
        writeInternalFile(PersonRepository.FILENAME, Gson().toJson(person))

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)
            val personCharacteristicView = CharacterView(activity)

            mainActivityView.clickOnCharacterButton()

            assertThat(personCharacteristicView.getFirstName()).isEqualTo(person.firstName)
            assertThat(personCharacteristicView.getLastName()).isEqualTo(person.lastName)
            assertThat(personCharacteristicView.getSex()).isEqualTo(person.sex)
            assertThat(personCharacteristicView.getOrigin()).isEqualTo(person.origin)
            assertThat(personCharacteristicView.getAge()).isEqualTo(person.age.toString())
        }
    }
}