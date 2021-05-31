package com.depromeet.linkzupzup.di

import android.app.AlarmManager
import androidx.activity.ComponentActivity
import com.depromeet.linkzupzup.component.LinkZupAlarmManager
import com.depromeet.linkzupzup.component.PreferencesManager
import com.depromeet.linkzupzup.component.RoomDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val commonModule = module {

    single { PreferencesManager(androidApplication()) }

    single { LinkZupAlarmManager(androidApplication()) }

    single { androidApplication().getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager }

    single { RoomDB.getInstance(androidApplication()) }

}