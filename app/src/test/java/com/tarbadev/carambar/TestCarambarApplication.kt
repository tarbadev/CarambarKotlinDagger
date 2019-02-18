package com.tarbadev.carambar

import android.app.Activity
import android.app.Application
import com.tarbadev.carambar.ui.DaggerTestApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import javax.inject.Inject

class TestCarambarApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        System.out.println("<========= TESTING APPLICATION ==========================>")
        DaggerTestApplicationComponent.create().inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingActivityInjector
    }
}