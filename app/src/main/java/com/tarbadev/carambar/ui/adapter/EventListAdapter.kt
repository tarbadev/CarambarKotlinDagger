package com.tarbadev.carambar.ui.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.tarbadev.carambar.R
import com.tarbadev.carambar.domain.EventList


class EventListAdapter(
    private val eventList: EventList,
    private val context: Context
) : RecyclerView.Adapter<EventListAdapter.EventListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)

        return EventListViewHolder(view)
    }

    override fun getItemCount(): Int = eventList.events.size

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        val ageTextView = holder.cardView.findViewById<TextView>(R.id.event_card_age)
        ageTextView.text = String.format("%s %d", holder.cardView.resources.getString(R.string.ageLabel), position)

        val eventListContainer = holder.cardView.findViewById<LinearLayout>(R.id.event_list)
        eventListContainer.removeAllViews()

        eventList.events[position]?.forEach { event ->
            val textView = TextView(context)
            textView.text = event
            textView.typeface = ResourcesCompat.getFont(context, R.font.indie_flower)
            textView.gravity = Gravity.START
            eventListContainer.addView(textView)
        }
    }

    class EventListViewHolder(val cardView: View) : RecyclerView.ViewHolder(cardView)
}