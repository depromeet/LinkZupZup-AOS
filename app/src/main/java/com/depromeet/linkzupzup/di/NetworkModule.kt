package com.depromeet.linkzupzup.di

import com.depromeet.linkzupzup.AppConst.CONNECTION_TIMEOUT
import com.depromeet.linkzupzup.AppConst.READ_TIMEOUT
import com.depromeet.linkzupzup.AppConst.WRITE_TIMEOUT
import com.depromeet.linkzupzup.BuildConfig

import com.depromeet.linkzupzup.component.PreferencesManager
import com.depromeet.linkzupzup.extensions.applySSL
import com.google.gson.GsonBuilder
import com.depromeet.linkzupzup.ApiUrl
import com.depromeet.linkzupzup.AppConst.AUTHHORIZATION_KEY
import com.depromeet.linkzupzup.AppConst.USER_ID_KEY
import com.depromeet.linkzupzup.architecture.domainLayer.entities.DefaultResponseEntity
import com.depromeet.linkzupzup.architecture.domainLayer.entities.ResponseEntity
import com.google.gson.Gson
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

    // single { GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create() }

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
                header(AUTHHORIZATION_KEY, pref.getAuthorization())
                // header(USER_ID_KEY, pref.getUserId())
                header(USER_ID_KEY, "2131")
            }.build())
        }
    }

//    single {
//        Interceptor {  chain ->
//            val newRequest = chain.request().newBuilder().build()
//            chain.proceed(newRequest).also { response ->
//
//                response.body()?.let { responseBody ->
//                    val buffer = responseBody.source()
//                        .buffer()
//                        .clone()
//
//                    val responseStr = buffer.readString(Charsets.UTF_8)
//                    Gson().fromJson(responseStr, DefaultResponseEntity::class.java).let {
//                        when (it.status) {
//                            // 상태값이 다른 API들에서 중복되어 사용되고 있어 의논이 필요
//
//                        }
//                    }
//                }
//            }
//        }
//    }

}