package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.architecture.presenterLayer.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ScrapDetailViewModel(get(), get()) }
    viewModel { AlarmDetailViewModel(get()) }
    viewModel { MyPageViewModel() }
    viewModel { MyDonutViewModel() }
    viewModel { LinkHistoryDetailViewModel(get()) }
    viewModel { WebViewViewModel(get()) }
    viewModel { IntroViewModel() }
    viewModel { OnBoardingViewModel() }
    viewModel { LoginViewModel(get()) }

}