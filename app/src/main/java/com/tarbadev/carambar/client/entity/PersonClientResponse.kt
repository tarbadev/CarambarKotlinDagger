package com.tarbadev.carambar.client.entity

data class PersonClientResponse(
    val gender: String,
    val name: PersonClientNameResponse,
    val nat: String
)
