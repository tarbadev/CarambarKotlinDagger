package com.tarbadev.carambar.ui.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCharacterFragment(): CharacterFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSettingsFragment(): SettingsFragment
}