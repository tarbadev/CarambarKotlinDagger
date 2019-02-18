package com.tarbadev.carambar

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector


@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, AppProvider::class])
interface ApplicationComponent: AndroidInjector<CarambarApplication>