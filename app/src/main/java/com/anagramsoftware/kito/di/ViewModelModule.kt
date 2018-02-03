package com.anagramsoftware.kito.di

import com.anagramsoftware.kito.ui.MainViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

/**
* Created by Udathari on 2/3/2018.
*/

val viewModelModule = applicationContext {

    viewModel { MainViewModel(get()) }

}