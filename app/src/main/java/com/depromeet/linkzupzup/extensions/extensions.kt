package com.depromeet.linkzupzup.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.depromeet.linkzupzup.base.BaseView
import com.depromeet.linkzupzup.component.SSLHelper
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun OkHttpClient.Builder.applySSL(): OkHttpClient.Builder = SSLHelper.configureClient(this)

fun <T> Single<T>.schedulers(subscribeOnScheduler: Scheduler = Schedulers.io(),
                             observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()): Single<T> {
    subscribeOn(subscribeOnScheduler)
    observeOn(observeOnScheduler)
    return this
}

fun <T> Single<T>.subSimple(onSuccess: Consumer<in T>?, baseView: BaseView<*>): Disposable {
    return subscribe(onSuccess, { baseView.defaultThrowable(it) })
}

@Composable
fun <T> T.mutableStateValue(): MutableState<T> = remember { mutableStateOf(this) }

/**
 * 참고 : https://meetup.toast.com/posts/130
 */
@Throws(ParseException::class)
fun String.toDate(formatStr: String = "yyyy-MM-dd'T'HH:mm:ss'Z'", timeZone: TimeZone = TimeZone.getTimeZone("Asia/seoul")): Date {
    return with(SimpleDateFormat(formatStr).also { it.timeZone = timeZone }) {
        parse(this@toDate)
    }
}
fun Date.dateFormat(toFormatStr: String, locale: Locale = Locale.KOREAN) = with(SimpleDateFormat(toFormatStr, locale)) {
    this.format(this@dateFormat)
}

fun Color.isEquals(target: Color): Boolean {
    return alpha == target.alpha &&
            red == target.red &&
            blue == target.blue &&
            green == target.green
}