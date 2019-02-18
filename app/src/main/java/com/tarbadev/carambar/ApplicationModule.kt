package com.tarbadev.carambar

import com.tarbadev.carambar.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ApplicationModule {
    @ContributesAndroidInjector
    fun contributeActivityInjector(): MainActivity
}