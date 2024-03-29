package com.tarbadev.carambar.blackbox.view

import android.app.Activity
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView

abstract class BaseView(protected val activity: Activity) {

    fun clickOnCharacterTab() {
        clickOnBottomNavigationTab(com.tarbadev.carambar.R.id.bottom_navigation_character)
    }

    fun clickOnHomeTab() {
        clickOnBottomNavigationTab(com.tarbadev.carambar.R.id.bottom_navigation_home)
    }

    fun clickOnSettingsTab() {
        clickOnBottomNavigationTab(com.tarbadev.carambar.R.id.bottom_navigation_settings)
    }

    protected fun getTextViewText(id: Int): String {
        val textView = activity.findViewById(id) as TextView
        return textView.text as String
    }

    protected fun clickOnButton(id: Int) {
        val textView = activity.findViewById(id) as Button
        textView.performClick()
    }

    private fun clickOnBottomNavigationTab(id: Int) {
        val textView = activity.findViewById(id) as BottomNavigationItemView
        textView.performClick()
    }
}