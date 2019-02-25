package com.tarbadev.carambar.blackbox.view

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.tarbadev.carambar.R
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

    fun getGraduates(): List<String> {
        val graduatesLayout = activity.findViewById<LinearLayout>(R.id.characterGraduates)
        return graduatesLayout.children.map { (it as TextView).text as String }.toList()
    }
}
