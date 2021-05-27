package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.dataSources.roomdb.RoomDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single {
        RoomDB.getInstance(androidApplication().applicationContext)
    }
}