package com.tarbadev.carambar

import dagger.Component
import dagger.android.AndroidInjector


@Component(modules = [CoreModule::class, AppProvider::class])
interface ApplicationComponent : AndroidInjector<CarambarApplication>