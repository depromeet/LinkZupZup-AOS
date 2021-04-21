package com.depromeet.linkzupzup.utils

import android.util.Log
import com.depromeet.linkzupzup.BuildConfig

object DLog {

    var DEBUG_STATUS: Int = Int.MIN_VALUE

    fun v(tag: String?, msg: String): Int = if (BuildConfig.DEBUG) Log.v(tag, msg) else DEBUG_STATUS

    fun v(tag: String?, msg: String, tr: Throwable?): Int = if (BuildConfig.DEBUG) Log.v(tag, msg, tr) else DEBUG_STATUS

    fun d(tag: String?, msg: String): Int = if (BuildConfig.DEBUG) Log.d(tag, msg) else DEBUG_STATUS

    fun d(tag: String?, msg: String, tr: Throwable?): Int = if (BuildConfig.DEBUG) Log.d(tag, msg, tr) else DEBUG_STATUS

    fun i(tag: String?, msg: String): Int = if (BuildConfig.DEBUG) Log.i(tag, msg) else DEBUG_STATUS

    fun i(tag: String?, msg: String, tr: Throwable?): Int = if (BuildConfig.DEBUG) Log.i(tag, msg, tr) else DEBUG_STATUS

    fun w(tag: String?, msg: String): Int = if (BuildConfig.DEBUG) Log.w(tag, msg) else DEBUG_STATUS

    fun w(tag: String?, msg: String, tr: Throwable?): Int = if (BuildConfig.DEBUG) Log.w(tag, msg, tr) else DEBUG_STATUS

    fun w(tag: String?, tr: Throwable?): Int = if (BuildConfig.DEBUG) Log.w(tag, tr) else DEBUG_STATUS

    fun e(tag: String?, msg: String): Int = if (BuildConfig.DEBUG) Log.e(tag, msg) else DEBUG_STATUS

    fun e(tag: String?, msg: String, tr: Throwable?): Int = if (BuildConfig.DEBUG) Log.e(tag, msg, tr) else DEBUG_STATUS

    fun wtf(tag: String?, msg: String): Int = if (BuildConfig.DEBUG) Log.wtf(tag, msg) else DEBUG_STATUS

    fun wtf(tag: String?, tr: Throwable): Int = if (BuildConfig.DEBUG) Log.wtf(tag, tr) else DEBUG_STATUS

    fun wtf(tag: String?, msg: String, tr: Throwable?): Int = if (BuildConfig.DEBUG) Log.wtf(tag, msg, tr) else DEBUG_STATUS

    fun println(priority: Int, tag: String?, msg: String): Int = if (BuildConfig.DEBUG) Log.println(priority, tag, msg) else DEBUG_STATUS

}