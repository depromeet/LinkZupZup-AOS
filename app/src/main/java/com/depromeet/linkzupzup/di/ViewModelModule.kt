package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.presenter.AlarmDetailViewModel
import com.depromeet.linkzupzup.presenter.LinkHistoryDetailViewModel
import com.depromeet.linkzupzup.presenter.MainViewModel
import com.depromeet.linkzupzup.presenter.ScrapDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ScrapDetailViewModel(get()) }
    viewModel { AlarmDetailViewModel(get()) }
    viewModel { MyPageViewModel() }
    viewModel { MyDonutViewModel() }
    viewModel { LinkHistoryDetailViewModel(get()) }
}