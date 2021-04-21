package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.AppConst.CONNECTION_TIMEOUT
import com.depromeet.linkzupzup.AppConst.DEVICE_TOKEN_KEY
import com.depromeet.linkzupzup.AppConst.DEVICE_TYPE
import com.depromeet.linkzupzup.AppConst.DEVICE_TYPE_KEY
import com.depromeet.linkzupzup.AppConst.READ_TIMEOUT
import com.depromeet.linkzupzup.AppConst.WRITE_TIMEOUT
import com.depromeet.linkzupzup.BuildConfig

import com.depromeet.linkzupzup.component.PreferencesManager
import com.depromeet.linkzupzup.extensions.applySSL
import com.google.gson.GsonBuilder
import com.depromeet.linkzupzup.ApiUrl
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }

    single { GsonBuilder().create() }

    single {
        OkHttpClient().newBuilder()
            .applySSL()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(get())
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            }).build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(ApiUrl.BASE_DOMAIN)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        Interceptor { chain ->
            val pref: PreferencesManager = get()
            chain.proceed(chain.request().newBuilder().apply {
                header(DEVICE_TYPE_KEY, DEVICE_TYPE)
                header(DEVICE_TOKEN_KEY, pref.getDeviceToken())
            }.build())
        }
    }

}