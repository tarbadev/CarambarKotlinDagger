package com.tarbadev.carambar.blackbox

import androidx.test.core.app.ActivityScenario
import com.google.gson.Gson
import com.tarbadev.carambar.Factory
import com.tarbadev.carambar.blackbox.view.CharacterView
import com.tarbadev.carambar.blackbox.view.HomeView
import com.tarbadev.carambar.blackbox.view.SettingsView
import com.tarbadev.carambar.blackbox.view.TopBarView
import com.tarbadev.carambar.repository.PersonRepository
import com.tarbadev.carambar.ui.activity.MainActivity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal

class TopBarBlackboxTest: BlackboxTest() {

    @Test
    fun `top bar is displayed everywhere and displays the current balance`() {
        val existingBalance = "$3,485.24"
        val existingPerson = Factory.person().copy(balance = BigDecimal.valueOf(3485.24))

        writeInternalFile(PersonRepository.FILENAME, Gson().toJson(existingPerson))

        ActivityScenario.launch(MainActivity::class.java).onActivity { activity ->
            val topBar = TopBarView(activity)

            var currentBalance = topBar.getCurrentBalance()
            assertThat(currentBalance).isEqualTo(existingBalance)

            val homeView = HomeView(activity)
            homeView.clickOnHomeTab()

            currentBalance = topBar.getCurrentBalance()
            assertThat(currentBalance).isEqualTo(existingBalance)

            val characteristicView = CharacterView(activity)
            characteristicView.clickOnCharacterTab()

            currentBalance = topBar.getCurrentBalance()
            assertThat(currentBalance).isEqualTo(existingBalance)

            val settingsView = SettingsView(activity)
            settingsView.clickOnSettingsTab()

            currentBalance = topBar.getCurrentBalance()
            assertThat(currentBalance).isEqualTo(existingBalance)
        }
    }
}