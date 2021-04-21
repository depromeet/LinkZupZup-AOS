package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.presenter.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}