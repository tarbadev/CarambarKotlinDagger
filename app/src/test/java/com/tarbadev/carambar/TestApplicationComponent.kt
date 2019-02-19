package com.tarbadev.carambar

import dagger.Component
import dagger.android.AndroidInjector

@Component(modules = [CoreModule::class, TestAppProvider::class])
interface TestApplicationComponent: AndroidInjector<TestCarambarApplication>