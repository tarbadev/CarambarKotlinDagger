package com.tarbadev.carambar

import dagger.Module
import com.tarbadev.carambar.ui.activity.MainActivity
import dagger.android.ContributesAndroidInjector

@Module
interface ApplicationModule {
    @ContributesAndroidInjector
    fun contributeActivityInjector(): MainActivity
}