package com.anagramsoftware.kito.ui.resultdetail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.anagramsoftware.kito.R
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

/**
* Created by udesh on 2/10/18.
*/

class ResultDetailActivity: AppCompatActivity() {

    private val viewModel by inject<ResultDetailViewModel>()

    // Views
    private val predictionIV = findViewById<ImageView>(R.id.prediction_iv)
    private val predictionTV = findViewById<TextView>(R.id.prediction_tv)
    private val confidenceTV = findViewById<TextView>(R.id.confidence_tv)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.create(intent.getStringExtra(EXTRA_RESULT_ID), intent.getStringExtra(EXTRA_IMAGE_URI))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> { onBackPressed() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        viewModel.apply {
            start()
            result.observe(this@ResultDetailActivity, Observer {
                it?.let {
                    predictionTV.text = if (it.containDengue) "Contains Dengue" else "Doesn't Contain Dengue"
                    confidenceTV.text = "${(it.confidence * 100).toInt()}%"
                }
            })
            imageUri.observe(this@ResultDetailActivity, Observer {
                Glide.with(this@ResultDetailActivity)
                        .load(it)
                        .into(predictionIV)
            })
        }

    }

    companion object {
        private const val TAG = "PredictionDetail"

        const val EXTRA_RESULT_ID = "id"
        const val EXTRA_IMAGE_URI = "image_uri"
    }

}