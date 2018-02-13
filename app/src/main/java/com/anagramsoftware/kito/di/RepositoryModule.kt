package com.anagramsoftware.kito.di

import android.arch.persistence.room.Room
import com.anagramsoftware.kito.data.source.Repository
import com.anagramsoftware.kito.data.source.local.LocalDataSource
import com.anagramsoftware.kito.data.source.local.LocalDatabase
import com.anagramsoftware.kito.data.source.remote.RemoteDataSource
import org.koin.dsl.module.applicationContext

/**
* Created by Udathari on 2/3/2018.
*/

val repositoryModule = applicationContext {

    factory { Room.databaseBuilder(get(), LocalDatabase::class.java, "database.db")
            .build() }

    bean { LocalDataSource(get()) }

    bean { RemoteDataSource(get(), get()) }

    bean { Repository(get(), get()) }


}