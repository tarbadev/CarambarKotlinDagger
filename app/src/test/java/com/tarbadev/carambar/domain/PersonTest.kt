package com.tarbadev.carambar.domain

import com.tarbadev.carambar.Factory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PersonTest {
    @Test
    fun `ageCategory changes depending on the age`() {
        var person = Factory.person().copy(age = 0)
        assertThat(person.ageCategory).isEqualTo(AgeCategory.BABY)

        person = person.copy(age = 2)
        assertThat(person.ageCategory).isEqualTo(AgeCategory.BABY)

        person = person.copy(age = 3)
        assertThat(person.ageCategory).isEqualTo(AgeCategory.CHILD)

        person = person.copy(age = 11)
        assertThat(person.ageCategory).isEqualTo(AgeCategory.CHILD)

        person = person.copy(age = 12)
        assertThat(person.ageCategory).isEqualTo(AgeCategory.TEEN)

        person = person.copy(age = 17)
        assertThat(person.ageCategory).isEqualTo(AgeCategory.TEEN)

        person = person.copy(age = 18)
        assertThat(person.ageCategory).isEqualTo(AgeCategory.ADULT)
    }
}