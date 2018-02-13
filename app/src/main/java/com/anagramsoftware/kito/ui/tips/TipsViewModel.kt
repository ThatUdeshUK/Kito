package com.anagramsoftware.kito.ui.tips

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.anagramsoftware.kito.data.model.Result
import com.anagramsoftware.kito.data.source.Repository
import com.anagramsoftware.kito.extentions.subscribeInBackgroundObserveOnMainThread
import io.reactivex.disposables.CompositeDisposable

/**
* Created by udesh on 2/11/18.
*/

class TipsViewModel(
        application: Application,
        private val repository: Repository
) : AndroidViewModel(application) {

    val TAG = javaClass.simpleName

    private val compositeDisposable = CompositeDisposable()

    val results: MutableLiveData<List<Result>> = MutableLiveData()

    fun start() {
        compositeDisposable.add(
                repository.getResults()
                        .subscribeInBackgroundObserveOnMainThread()
                        .subscribe({
                            Log.d(TAG, "Success ${it.size}")
                            results.value = it
                        }, {
                            Log.d(TAG, "Error $it")
                        })
        )
    }

    fun stop() {
        compositeDisposable.dispose()
    }

}