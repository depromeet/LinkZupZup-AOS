package com.depromeet.linkzupzup

import androidx.multidex.MultiDexApplication
import com.depromeet.linkzupzup.di.*
import com.depromeet.linkzupzup.receiver.NotificationSetting
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class AppApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // Notification ChannelID 생성
        NotificationSetting.initChannelId(this)

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)

            koin.loadModules(listOf(
                commonModule,
                networkModule,
                apiModule,
                dataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule))
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }

}