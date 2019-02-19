package com.tarbadev.carambar.blackbox.view

import com.tarbadev.carambar.ui.activity.MainActivity

class HomeView(activity: MainActivity): BaseView(activity) {

    fun clickOnAgeButton() {
        clickOnButton(com.tarbadev.carambar.R.id.ageButton)
    }
}