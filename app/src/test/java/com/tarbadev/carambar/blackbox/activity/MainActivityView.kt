package com.tarbadev.carambar.blackbox.activity

import android.widget.Button
import android.widget.TextView
import com.tarbadev.carambar.ui.activity.MainActivity

class MainActivityView(private val activity: MainActivity) {
    fun getFirstName(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.newCharacterFirstName)
    }
    
    fun getLastName(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.newCharacterLastName)
    }
    
    fun getSex(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.newCharacterSex)
    }
    
    fun getOrigin(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.newCharacterOrigin)
    }
    
    fun getAge(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.newCharacterAge)
    }

    fun getAgeCategory(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.newCharacterAgeCategory)
    }

    fun clickOnAge() {
        val ageButton = activity.findViewById(com.tarbadev.carambar.R.id.ageButton) as Button
        ageButton.performClick()
    }

    private fun getTextViewText(id: Int): String {
        val textView = activity.findViewById(id) as TextView
        return textView.text as String
    }
}