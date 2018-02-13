package com.anagramsoftware.kito.data.source.remote.customvision

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
* Created by Udathari on 2/3/2018.
*/

data class Predictions(
        @SerializedName("Id") @Expose var id: UUID,
        @SerializedName("Project") @Expose var project: UUID,
        @SerializedName("Iteration") @Expose var iteration: UUID,
        @SerializedName("Created") @Expose var created: Date,
        @SerializedName("Predictions") @Expose var predictions: List<Prediction?>? = null
)
