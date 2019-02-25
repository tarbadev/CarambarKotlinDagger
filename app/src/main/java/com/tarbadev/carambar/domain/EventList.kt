package com.tarbadev.carambar.domain

data class EventList(val events: MutableMap<Int, MutableList<String>> = mutableMapOf())