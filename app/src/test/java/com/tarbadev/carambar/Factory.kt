package com.tarbadev.carambar

import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.domain.School
import com.tarbadev.carambar.domain.Sex
import java.math.BigDecimal

class Factory {
    companion object {
        fun person(): Person {
            return Person(
                firstName = "John",
                lastName = "Doe",
                sex = Sex.MALE,
                origin = "United States",
                age = 0,
                school = School.NONE,
                balance = BigDecimal.valueOf(3458.26)
            )
        }
    }
}