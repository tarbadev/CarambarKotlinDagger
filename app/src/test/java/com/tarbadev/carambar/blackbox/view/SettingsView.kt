package com.tarbadev.carambar.blackbox.view

import androidx.appcompat.app.AlertDialog
import com.tarbadev.carambar.R
import com.tarbadev.carambar.ui.activity.MainActivity
import org.robolectric.shadows.ShadowAlertDialog

class SettingsView(activity: MainActivity): BaseView(activity) {
    fun clickOnEndLifeButton() {
        clickOnButton(R.id.endLifeButton)
    }

    fun clickOnValidEndLifeButton() {
        (ShadowAlertDialog.getLatestDialog() as AlertDialog)
            .getButton(AlertDialog.BUTTON_POSITIVE)
            .performClick()
    }
}
