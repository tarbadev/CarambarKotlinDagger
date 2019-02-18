package com.tarbadev.carambar.domain

data class Person(
    val firstName: String,
    val lastName: String,
    val sex: String,
    val origin: String,
    val age: Int = 0,
    val ageCategory: AgeCategory
)