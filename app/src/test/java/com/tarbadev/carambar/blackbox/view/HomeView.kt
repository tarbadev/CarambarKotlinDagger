package com.tarbadev.carambar.blackbox.view

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.tarbadev.carambar.R
import com.tarbadev.carambar.ui.activity.MainActivity

class HomeView(private val activity: MainActivity): BaseView(activity) {

    fun clickOnAgeButton() {
        clickOnButton(R.id.ageButton)
    }

    fun getEvents(): List<String> {
        val eventLayout = activity.findViewById<LinearLayout>(R.id.event_list)
        return eventLayout.children.map { (it as TextView).text as String }.toList()
    }
}