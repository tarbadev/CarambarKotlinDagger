package com.tarbadev.carambar

import dagger.Module
import dagger.Provides
import org.springframework.web.client.RestTemplate
import javax.inject.Named

@Module
class TestAppProvider {
    @Provides
    @Named("personClientUrl")
    fun providePersonClientUrl(): String {
        return "http://localhost:8888/"
    }

    @Provides
    @Named("restTemplate")
    fun provideRestTemplate(): RestTemplate {
        return RestTemplate()
    }
}