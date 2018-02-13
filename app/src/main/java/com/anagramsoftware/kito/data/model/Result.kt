package com.anagramsoftware.kito.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
* Created by udesh on 2/11/18.
*/

@Entity
data class Result @JvmOverloads constructor(
        @PrimaryKey val id: String,
        val imageUri: String,
        val containDengue: Boolean,
        val confidence: Double,
        @Ignore var tags: List<Tag>? = null
)