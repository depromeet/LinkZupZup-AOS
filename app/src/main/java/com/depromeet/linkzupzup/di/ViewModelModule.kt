package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.architecture.presenterLayer.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ScrapDetailViewModel(get(), get(), get()) }
    viewModel { AlarmDetailViewModel(get()) }
    viewModel { MyPageViewModel(get()) }
    viewModel { MyDonutViewModel(get()) }
    viewModel { LinkHistoryDetailViewModel(get()) }
    viewModel { WebViewViewModel(get()) }
    viewModel { IntroViewModel() }
    viewModel { OnBoardingViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { TermsAndInfoViewModel() }

}