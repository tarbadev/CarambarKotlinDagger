package com.tarbadev.carambar.repository

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.tarbadev.carambar.annotation.AllOpen
import com.tarbadev.carambar.domain.EventList
import javax.inject.Inject
import javax.inject.Named

@AllOpen
class EventListRepository @Inject constructor(
    @Named("ApplicationContext") val applicationContext: Context
)
    : InternalFileRepository<EventList>(FILENAME, applicationContext, object: TypeToken<EventList>() {}.type) {
    companion object {
        const val FILENAME = "eventList.json"
    }
}
