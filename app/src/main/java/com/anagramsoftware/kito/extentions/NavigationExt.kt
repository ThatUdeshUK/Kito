package com.anagramsoftware.kito.extentions

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.transition.Fade
import com.anagramsoftware.kito.R
import com.anagramsoftware.kito.ui.resultdetail.ResultDetailActivity
import com.anagramsoftware.kito.ui.tips.TipsFragment

/**
* Created by udesh on 2/10/18.
*/

const val main_container = R.id.container

fun FragmentManager.navigateToTips() {
    popBackStack()
    val fragment = TipsFragment.create()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        fragment.enterTransition = Fade()
        fragment.exitTransition = Fade()
    }
    this.beginTransaction().replace(main_container, fragment)
            .addToBackStack(null)
            .commit()
}

fun Fragment.navigateToPredictionDetail(predictionId: String? = null, imageUri: Uri? = null) {
    val intent = Intent(context, ResultDetailActivity::class.java)
    predictionId?.let { intent.putExtra(ResultDetailActivity.EXTRA_RESULT_ID, it) }
    imageUri?.let { intent.putExtra(ResultDetailActivity.EXTRA_IMAGE_URI, it.toString()) }
    startActivity(intent)
}