package com.anagramsoftware.kito.ui.resultdetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.net.Uri
import com.anagramsoftware.kito.data.model.Result
import com.anagramsoftware.kito.data.source.Repository
import com.anagramsoftware.kito.extentions.subscribeInBackgroundObserveOnMainThread

/**
* Created by udesh on 2/10/18.
*/

class ResultDetailViewModel(
        application: Application,
        private val repository: Repository
): AndroidViewModel(application) {

    private var resultId: String? = null

    var imageUri: MutableLiveData<Uri> = MutableLiveData()
    val result: MutableLiveData<Result> = MutableLiveData()

    fun create(resultId: String? = null, imageUri: String? = null) {
        if (resultId == null && imageUri != null) {
            this.imageUri.value = Uri.parse(imageUri)
            predict()
        } else if (resultId != null && imageUri == null) {
            this.resultId = resultId
            loadResult()
        } else {
            throw Exception("Either parameters predictionId or imagePath can be passed")
        }
    }

    fun start() {
    }

    private fun predict() {
        imageUri.value?.let {
            repository.predictImage(it)
                    .subscribeInBackgroundObserveOnMainThread()
                    .subscribe({
                        result.value = it
                    }, {

                    })
        }
    }

    private fun loadResult() {
        resultId?.let {
            repository.getResult(it)
                    .subscribeInBackgroundObserveOnMainThread()
                    .subscribe({
                        imageUri.value = Uri.parse(it.imageUri)
                        result.value = it
                    }, {})
        }
    }

    companion object {
        private const val TAG = "ResultDetailViewModel"
    }

}