package com.tarbadev.carambar

import dagger.Module
import dagger.Provides
import org.springframework.web.client.RestTemplate
import javax.inject.Named

@Module
class AppProvider {
    @Provides
    @Named("personClientUrl")
    fun providePersonClientUrl(): String {
        return "https://randomuser.me/api/"
    }

    @Provides
    @Named("restTemplate")
    fun provideRestTemplate(): RestTemplate {
        return RestTemplate()
    }
}