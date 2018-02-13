package com.anagramsoftware.kito.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
* Created by udesh on 2/11/18.
*/

@Entity(foreignKeys = [
    ForeignKey(
            entity = Result::class,
            parentColumns = ["id"],
            childColumns = ["resultId"],
            onDelete = ForeignKey.CASCADE
            )
])
data class Tag(
        @PrimaryKey val id: String,
        val tag: String,
        val confidence: Double,
        val resultId: String
        )