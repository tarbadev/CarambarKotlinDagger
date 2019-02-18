package com.tarbadev.carambar

import com.tarbadev.carambar.domain.Person

class Factory {
    companion object {
        fun person(): Person {
            return Person(
                firstName = "John",
                lastName = "Doe",
                sex = "Male",
                origin = "United States",
                age = 0
            )
        }
    }
}