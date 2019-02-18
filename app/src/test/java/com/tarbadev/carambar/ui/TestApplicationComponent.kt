package com.tarbadev.carambar.ui

import com.tarbadev.carambar.ApplicationModule
import com.tarbadev.carambar.TestAppProvider
import com.tarbadev.carambar.TestCarambarApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, TestAppProvider::class])
interface TestApplicationComponent: AndroidInjector<TestCarambarApplication>