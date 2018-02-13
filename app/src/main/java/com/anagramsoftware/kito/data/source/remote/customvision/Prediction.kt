package com.anagramsoftware.kito.data.source.remote.customvision

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Udathari on 2/3/2018.
 */

data class Prediction(
        @SerializedName("TagId") @Expose var tagId: UUID,
        @SerializedName("Tag") @Expose var tag: String? = null,
        @SerializedName("Probability") @Expose var probability: Double
)