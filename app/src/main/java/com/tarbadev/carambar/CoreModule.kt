package com.tarbadev.carambar

import com.tarbadev.carambar.ui.fragment.FragmentModule
import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(includes = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    FragmentModule::class,
    ContextModule::class
])
interface CoreModule