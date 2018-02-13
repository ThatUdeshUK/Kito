package com.anagramsoftware.kito.extentions

import android.os.Build
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.transition.Fade

/**
* Created by udesh on 2/10/18.
*/

//  Extension to add fragments to activities
fun AppCompatActivity.replaceFragmentToActivity(fragment: Fragment, frameId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        fragment.enterTransition = Fade()
        fragment.exitTransition = Fade()
    }
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(frameId, fragment)
    transaction.commit()
}
