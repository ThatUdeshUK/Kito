package com.anagramsoftware.kito.data.source.local

import com.anagramsoftware.kito.data.model.Result
import com.anagramsoftware.kito.data.model.Tag
import com.anagramsoftware.kito.extentions.subscribeInBackgroundObserveOnMainThread
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
* Created by udesh on 2/11/18.
*/

class LocalDataSource(
        private val localDatabase: LocalDatabase
) {

    // Predictions
    fun insertResult(result: Result, tags: List<Tag>?): Single<Boolean> {
        return Observable.fromCallable {
            localDatabase.resultDao().insert(result)
            tags?.let { localDatabase.tagDao().insert(it) }
            return@fromCallable true
        }.subscribeInBackgroundObserveOnMainThread()
                .firstOrError()
    }

    fun getResult(id: String): Single<Result> = localDatabase.resultDao().get(id)

    fun getResults(): Flowable<List<Result>> = localDatabase.resultDao().getAll()

}