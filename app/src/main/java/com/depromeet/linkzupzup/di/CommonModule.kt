package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.component.PreferencesManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val commonModule = module {

    single { PreferencesManager(androidApplication()) }

}