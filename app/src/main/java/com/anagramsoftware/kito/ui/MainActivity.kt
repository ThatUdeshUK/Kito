package com.anagramsoftware.kito.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.anagramsoftware.kito.R
import com.anagramsoftware.kito.extentions.main_container
import com.anagramsoftware.kito.extentions.navigateToTips
import com.anagramsoftware.kito.extentions.replaceFragmentToActivity
import com.anagramsoftware.kito.ui.identify.IdentifyFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        IdentifyFragment.create().also {
            replaceFragmentToActivity(it, main_container)
            bottomNavigation.selectedItemId = R.id.action_identify
        }

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_identify -> {
                    supportFragmentManager.popBackStack()
                    true
                }
                R.id.action_tips-> {
                    supportFragmentManager.navigateToTips()
                    true
                }
                R.id.action_map -> {
                    true
                }
                else -> false
            }
        }
    }
}
