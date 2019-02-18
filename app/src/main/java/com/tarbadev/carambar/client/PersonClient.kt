package com.tarbadev.carambar.client

import com.tarbadev.carambar.annotation.AllOpen
import com.tarbadev.carambar.client.entity.PersonClientResponse
import com.tarbadev.carambar.client.entity.PersonClientResultResponse
import com.tarbadev.carambar.domain.Person
import com.tarbadev.carambar.domain.AgeCategory
import org.springframework.web.client.RestTemplate
import javax.inject.Inject
import javax.inject.Named

@AllOpen
class PersonClient @Inject constructor(
    @Named("personClientUrl") private val apiUrl: String,
    @Named("restTemplate") private val restTemplate: RestTemplate
) {

    private val mapOriginResponseToDomain = mapOf(
        Pair("AU", "Australia"),
        Pair("BR", "Brazil"),
        Pair("CA", "Canada"),
        Pair("CH", "Switzerland"),
        Pair("DE", "Germany"),
        Pair("DK", "Denmark"),
        Pair("ES", "Spain"),
        Pair("FI", "Finland"),
        Pair("FR", "France"),
        Pair("GB", "United Kingdom"),
        Pair("IE", "Ireland"),
        Pair("IR", "Iran"),
        Pair("NO", "Norway"),
        Pair("NL", "Netherlands"),
        Pair("NZ", "New Zealand"),
        Pair("TR", "Turkey"),
        Pair("US", "United States")
    )

    fun generateNewPerson(): Person {
        val clientResponse = restTemplate.getForEntity(apiUrl, PersonClientResultResponse::class.java)

        return responseToDomain(clientResponse.body.results[0])
    }

    fun responseToDomain(personClientResponse: PersonClientResponse): Person {
        return Person(
            firstName = personClientResponse.name.first.capitalize(),
            lastName = personClientResponse.name.last.capitalize(),
            sex = personClientResponse.gender.capitalize(),
            origin = mapOriginResponseToDomain.getValue(personClientResponse.nat),
            ageCategory = AgeCategory.BABY
        )
    }
}
