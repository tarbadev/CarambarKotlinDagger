package com.tarbadev.carambar.blackbox.activity

import android.widget.Button
import android.widget.TextView
import com.tarbadev.carambar.ui.activity.MainActivity

class MainActivityView(private val activity: MainActivity) {
    fun getFirstName(): String {
        val firstName = activity.findViewById(com.tarbadev.carambar.R.id.newCharacterFirstName) as TextView
        return firstName.text as String
    }
    
    fun getLastName(): String {
        val lastName = activity.findViewById(com.tarbadev.carambar.R.id.newCharacterLastName) as TextView
        return lastName.text as String
    }
    
    fun getSex(): String {
        val sex = activity.findViewById(com.tarbadev.carambar.R.id.newCharacterSex) as TextView
        return sex.text as String
    }
    
    fun getOrigin(): String {
        val origin = activity.findViewById(com.tarbadev.carambar.R.id.newCharacterOrigin) as TextView
        return origin.text as String
    }
    
    fun getAge(): String {
        val age = activity.findViewById(com.tarbadev.carambar.R.id.newCharacterAge) as TextView
        return age.text as String
    }

    fun clickOnAge() {
        val ageButton = activity.findViewById(com.tarbadev.carambar.R.id.ageButton) as Button
        ageButton.performClick()
    }
}