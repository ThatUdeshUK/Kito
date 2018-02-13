package com.anagramsoftware.kito.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.anagramsoftware.kito.data.model.Result
import com.anagramsoftware.kito.data.model.Tag

/**
* Created by udesh on 2/11/18.
*/

@Database(entities = [Result::class, Tag::class], version = 1)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun resultDao(): ResultDao

    abstract fun tagDao(): TagDao

}