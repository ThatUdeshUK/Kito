package com.anagramsoftware.kito.data.source.remote.customvision

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.POST

/**
* Created by Udathari on 2/3/2018.
*/
interface CustomVisionService {

    @POST("image")
    fun predictImage(): Single<Predictions>

}