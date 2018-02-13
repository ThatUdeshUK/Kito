package com.anagramsoftware.kito.data.source

import android.net.Uri
import com.anagramsoftware.kito.data.model.Result
import com.anagramsoftware.kito.data.source.local.LocalDataSource
import com.anagramsoftware.kito.data.source.remote.RemoteDataSource
import com.anagramsoftware.kito.data.source.remote.customvision.Predictions
import io.reactivex.Single
import java.io.File

/**
* Created by Udathari on 2/3/2018.
*/

class Repository(
        private val localDataSource: LocalDataSource,
        private val remoteDataSource: RemoteDataSource
) {

    fun predictImage(imageUri: Uri): Single<Result> = remoteDataSource.predictImage(imageUri)

    fun getResults() = localDataSource.getResults()

    fun getResult(id: String) = localDataSource.getResult(id)

}