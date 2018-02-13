package com.anagramsoftware.kito.di

import com.anagramsoftware.kito.ui.identify.IdentifyViewModel
import com.anagramsoftware.kito.ui.resultdetail.ResultDetailViewModel
import com.anagramsoftware.kito.ui.tips.TipsViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

/**
* Created by Udathari on 2/3/2018.
*/

val viewModelModule = applicationContext {

    viewModel { IdentifyViewModel(get()) }

    viewModel { TipsViewModel(get(), get()) }

    viewModel { ResultDetailViewModel(get(), get()) }

}