package com.anagramsoftware.kito.data.source.local

import android.arch.persistence.room.*
import com.anagramsoftware.kito.data.model.Tag
import io.reactivex.Flowable

/**
* Created by udesh on 2/11/18.
*/

@Dao
interface TagDao {

    @Insert
    fun insert(tag: Tag)

    @Insert
    fun insert(tags: List<Tag>)

    @Update
    fun update(tag: Tag)

    @Delete
    fun delete(tag: Tag)

    @Query("SELECT * FROM tag")
    fun getAll(): Flowable<Tag>

    @Query("SELECT * FROM tag WHERE resultId=:resultId")
    fun getAllForPrediction(resultId: String): Flowable<Tag>

}