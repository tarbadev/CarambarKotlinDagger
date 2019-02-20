package com.tarbadev.carambar.blackbox

import androidx.test.core.app.ActivityScenario
import com.google.gson.Gson
import com.tarbadev.carambar.Factory
import com.tarbadev.carambar.blackbox.view.HomeView
import com.tarbadev.carambar.blackbox.view.CharacterView
import com.tarbadev.carambar.repository.PersonRepository
import com.tarbadev.carambar.ui.activity.MainActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CharacterBlackboxTest: BlackboxTest() {

    @Test
    fun `clicking on the character button shows person characteristics`() {
        enqueueSuccessGenerationResponse()

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val mainActivityView = HomeView(activity)

            mainActivityView.clickOnCharacterTab()

            val personCharacteristicView = CharacterView(activity)

            assertThat(personCharacteristicView.getFirstName()).isEqualTo("John")
            assertThat(personCharacteristicView.getLastName()).isEqualTo("Doe")
            assertThat(personCharacteristicView.getSex()).isEqualTo("Male")
            assertThat(personCharacteristicView.getOrigin()).isEqualTo("United States")
            assertThat(personCharacteristicView.getAge()).isEqualTo("0")
            assertThat(personCharacteristicView.getAgeCategory()).isEqualTo("Baby")

            val person = Factory.person()

            assertThat(getInternalFileContent(PersonRepository.FILENAME)).isEqualTo(Gson().toJson(person))
        }
    }
}