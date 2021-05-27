package com.depromeet.linkzupzup

import androidx.multidex.MultiDexApplication
import com.depromeet.linkzupzup.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class AppApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)

            koin.loadModules(listOf(
                commonModule,
                networkModule,
                apiModule,
                viewModelModule,
                useCaseModule,
                repositoryModule,
                roomModule))
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }

}