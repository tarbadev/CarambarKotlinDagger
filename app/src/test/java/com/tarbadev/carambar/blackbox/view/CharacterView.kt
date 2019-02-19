package com.tarbadev.carambar.blackbox.view

import com.tarbadev.carambar.ui.activity.MainActivity

class CharacterView(activity: MainActivity): BaseView(activity) {
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
}
