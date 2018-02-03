package com.anagramsoftware.kito

import android.app.Application
import com.anagramsoftware.kito.di.appModule
import org.koin.android.ext.android.startKoin

/**
* Created by Udathari on 2/3/2018.
*/

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, appModule)
    }

}