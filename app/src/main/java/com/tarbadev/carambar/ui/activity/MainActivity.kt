package com.tarbadev.carambar.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tarbadev.carambar.R
import com.tarbadev.carambar.ui.fragment.CharacterFragment
import com.tarbadev.carambar.ui.fragment.HomeFragment
import com.tarbadev.carambar.ui.fragment.SettingsFragment
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentTab(HomeFragment())

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            var selectedTab: Fragment = HomeFragment()
            when (it.itemId) {
                R.id.bottom_navigation_character -> selectedTab = CharacterFragment()
                R.id.bottom_navigation_settings -> selectedTab = SettingsFragment()
            }

            setCurrentTab(selectedTab)

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setCurrentTab(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.commit()
    }
}
