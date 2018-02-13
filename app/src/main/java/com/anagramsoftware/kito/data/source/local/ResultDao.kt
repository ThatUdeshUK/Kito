package com.anagramsoftware.kito.data.source.local

import android.arch.persistence.room.*
import com.anagramsoftware.kito.data.model.Result
import io.reactivex.Flowable
import io.reactivex.Single


/**
* Created by udesh on 2/11/18.
*/

@Dao
interface ResultDao {

    @Insert
    fun insert(result: Result)

    @Update
    fun update(result: Result)

    @Delete
    fun delete(result: Result)

    @Query("SELECT * FROM result WHERE id=:id")
    fun get(id: String): Single<Result>

    @Query("SELECT * FROM result")
    fun getAll(): Flowable<List<Result>>

}