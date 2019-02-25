package com.tarbadev.carambar.blackbox

import androidx.test.core.app.ActivityScenario
import com.google.gson.Gson
import com.tarbadev.carambar.Factory
import com.tarbadev.carambar.blackbox.view.CharacterView
import com.tarbadev.carambar.domain.School
import com.tarbadev.carambar.repository.PersonRepository
import com.tarbadev.carambar.ui.activity.MainActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal

class CharacterBlackboxTest: BlackboxTest() {

    @Test
    fun `clicking on the character button shows person characteristics`() {
        enqueueSuccessGenerationResponse()

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val personCharacteristicView = CharacterView(activity)
            personCharacteristicView.clickOnCharacterTab()

            assertThat(personCharacteristicView.getFirstName()).isEqualTo("John")
            assertThat(personCharacteristicView.getLastName()).isEqualTo("Doe")
            assertThat(personCharacteristicView.getSex()).isEqualTo("Male")
            assertThat(personCharacteristicView.getOrigin()).isEqualTo("United States")
            assertThat(personCharacteristicView.getAge()).isEqualTo("0")
            assertThat(personCharacteristicView.getAgeCategory()).isEqualTo("Baby")
            assertThat(personCharacteristicView.getSchool()).isEqualTo("N/A")

            val person = Factory.person().copy(balance = BigDecimal.ZERO)

            assertThat(getInternalFileContent(PersonRepository.FILENAME)).isEqualTo(Gson().toJson(person))
        }
    }

    @Test
    fun `character tab displays degrees`() {
        val person = Factory.person().copy(age = 32, school = School.NONE, graduates = mutableListOf(School.MIDDLE_SCHOOL, School.HIGH_SCHOOL))
        writeInternalFile(PersonRepository.FILENAME, Gson().toJson(person))

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val personCharacteristicView = CharacterView(activity)
            personCharacteristicView.clickOnCharacterTab()

            assertThat(personCharacteristicView.getGraduates()).isEqualTo(listOf(
                School.MIDDLE_SCHOOL.displayName,
                School.HIGH_SCHOOL.displayName
            ))
        }
    }
}