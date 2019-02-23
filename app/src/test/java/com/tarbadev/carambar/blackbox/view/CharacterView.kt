package com.tarbadev.carambar.blackbox.view

import com.tarbadev.carambar.ui.activity.MainActivity

class CharacterView(activity: MainActivity): BaseView(activity) {
    fun getFirstName(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.characterFirstName)
    }

    fun getLastName(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.characterLastName)
    }

    fun getSex(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.characterSex)
    }

    fun getOrigin(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.characterOrigin)
    }

    fun getAge(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.characterAge)
    }

    fun getAgeCategory(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.characterAgeCategory)
    }

    fun getSchool(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.characterSchool)
    }
}
