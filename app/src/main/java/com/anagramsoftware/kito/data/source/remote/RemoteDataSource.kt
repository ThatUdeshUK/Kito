package com.anagramsoftware.kito.data.source.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import com.anagramsoftware.kito.data.model.Result
import com.anagramsoftware.kito.data.model.Tag
import com.anagramsoftware.kito.data.source.local.LocalDataSource
import com.anagramsoftware.kito.data.source.remote.customvision.CustomVisionService
import com.anagramsoftware.kito.data.source.remote.customvision.Prediction
import com.anagramsoftware.kito.extentions.subscribeInBackgroundObserveOnMainThread
import com.anagramsoftware.kito.extentions.toSizeLimitedByteArray
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

/**
* Created by Udathari on 2/3/2018.
*/

class RemoteDataSource(
        context: Context,
        private val localDataSource: LocalDataSource
) {
    private val TAG = javaClass.simpleName

    private val contentResolver = context.contentResolver

    // Custom Vision
    companion object {
        private const val customVisionUrl =
                "https://southcentralus.api.cognitive.microsoft.com/customvision/v1.1/Prediction/1f819515-4801-499c-89c2-793f47267183/"
    }

    object Tags {
        val CONTAINS_DENGUE: UUID = UUID.fromString("db7cb5ae-31fe-4c3a-9d73-90fa26f3aa6a")
        val DOESNT_CONTAINS_DENGUE: UUID = UUID.fromString("43005785-ce32-40d0-9807-43a29f1e0463")
    }

    fun predictImage(imageUri: Uri): Single<Result> {
        return Observable.fromCallable { imageUri.toSizeLimitedByteArray(contentResolver)
                ?.let { createCustomVisionService(customVisionUrl, it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .firstOrError()
                .flatMap {
                    it.predictImage()
                            .map { predictions ->
                                var containsPrediction: Prediction? = null
                                var doesntContainsPrediction: Prediction? = null
                                val tags: ArrayList<Tag> = ArrayList()
                                predictions.predictions?.forEach {
                                    when {
                                        it?.tagId == Tags.CONTAINS_DENGUE -> containsPrediction = it
                                        it?.tagId == Tags.DOESNT_CONTAINS_DENGUE -> doesntContainsPrediction = it
                                        else -> it?.let { tags.add(Tag(it.tagId.toString(),
                                                it.tag ?: "", it.probability, predictions.id.toString())) }
                                    }

                                }
                                var containsDengue = false
                                var confidence = 0.0
                                doesntContainsPrediction?.let {
                                    containsPrediction?.let { it1 ->
                                        if (it.probability < it1.probability) {
                                            containsDengue = true
                                            confidence = it1.probability
                                        } else {
                                            containsDengue = false
                                            confidence = it.probability
                                        }
                                    }
                                }
                                return@map Result(predictions.id.toString(), imageUri.toString(), containsDengue, confidence)
                            }
                }
                .doOnSuccess {
                    Log.d(TAG, "doOnSuccess")
                    localDataSource.insertResult(
                            it, it.tags
                    ).subscribeInBackgroundObserveOnMainThread()
                            .subscribe({}, {})
                }
    }

    private fun createCustomVisionClient(bitmapByteArray: ByteArray): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Prediction-Key", "981fef67480a4711b69ac11a98bc8ee3")
            requestBuilder.addHeader("Content-Type", "application/octet-stream")
            requestBuilder.post(RequestBody.create(MediaType.parse("application/octet-stream"), bitmapByteArray))
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build()
    }

    private fun createCustomVisionService(baseUrl: String, bitmapByteArray: ByteArray): CustomVisionService {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createCustomVisionClient(bitmapByteArray))
                .build().create(CustomVisionService::class.java)
    }

}