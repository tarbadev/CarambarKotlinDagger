package com.tarbadev.carambar

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector

import javax.inject.Inject

class TestCarambarApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        System.out.println("<========================== RUNNING TEST APPLICATION ==========================>")
        DaggerTestApplicationComponent
            .builder()
            .contextModule(ContextModule(applicationContext))
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingFragmentInjector
}