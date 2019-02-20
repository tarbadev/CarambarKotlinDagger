package com.tarbadev.carambar

import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.domain.Sex

class Factory {
    companion object {
        fun person(): Person {
            return Person(
                firstName = "John",
                lastName = "Doe",
                sex = Sex.MALE,
                origin = "United States",
                age = 0
            )
        }
    }
}