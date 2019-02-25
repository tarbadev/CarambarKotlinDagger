package com.tarbadev.carambar.blackbox.view

import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.tarbadev.carambar.R
import com.tarbadev.carambar.ui.activity.MainActivity

class HomeView(activity: MainActivity) : BaseView(activity) {

    fun clickOnAgeButton() {
        clickOnButton(R.id.ageButton)
    }

    fun getEvents(): List<EventView> {
        val eventContainer = activity.findViewById<RecyclerView>(R.id.event_list_container)
        return eventContainer.children.map {cardView ->
            val card = (cardView as CardView)

            return@map EventView(
                card.findViewById<TextView>(R.id.event_card_age).text as String,
                card.findViewById<LinearLayout>(R.id.event_list)
                    .children
                    .map { (it as TextView).text as String }
                    .toList()
            )
        }.toList()
    }
}