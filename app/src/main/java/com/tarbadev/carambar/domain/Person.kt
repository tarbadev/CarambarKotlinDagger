package com.tarbadev.carambar.domain

data class Person(
    val firstName: String,
    val lastName: String,
    val sex: Sex,
    val origin: String,
    val age: Int = 0,
    val school: School = School.NONE
) {
    val ageCategory: AgeCategory
    get() {
        return when {
            age >= 18 -> AgeCategory.ADULT
            age >= 12 -> AgeCategory.TEEN
            age >= 3 -> AgeCategory.CHILD
            else -> AgeCategory.BABY
        }
    }
}
