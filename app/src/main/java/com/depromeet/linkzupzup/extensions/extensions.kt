package com.depromeet.linkzupzup.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
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


//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()
//return sp dimension value in pixels
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()
fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()
//converts px value into dip or sp
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density
fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity
fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

//the same for the views
fun View.dip(value: Int): Int = context.dip(value)
fun View.dip(value: Float): Int = context.dip(value)
fun View.sp(value: Int): Int = context.sp(value)
fun View.sp(value: Float): Int = context.sp(value)
fun View.px2dip(px: Int): Float = context.px2dip(px)
fun View.px2sp(px: Int): Float = context.px2sp(px)
fun View.dimen(@DimenRes resource: Int): Int = context.dimen(resource)

inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
    interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

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

@Composable
fun LazyItemScope.topSpacer(index: Int, size: Dp) = if (index == 0) Spacer(Modifier.height(size)) else null

@Composable
fun LazyItemScope.bottomSpacer(index: Int, items: List<*>, size: Dp) = if (index >= items.size - 1) Spacer(Modifier.height(size)) else null

fun toast(ctx: Context, msg: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(ctx, msg, duration).show()

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