package com.depromeet.linkzupzup.utils

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

object DeviceUtils {

    /**
     *  size.x = width, size.y = hegiht
     */
    fun getDeviceSize(context: Context): Point? = with(context) {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && display != null) {
            val metrics= DisplayMetrics()
            display?.getRealMetrics(metrics)?.let {
                Point(metrics.widthPixels, metrics.heightPixels)
            }
        } else {
            (getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.defaultDisplay?.let { display ->
                Point().also { size ->
                    display.getSize(size)
                }
            }
        }
    }

    fun showKeyboard(context: Context, view : View) = with(context) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).let { inputMethodManager ->
            inputMethodManager.showSoftInput(view, 0)
        }
    }

    fun hideKeyboard(context: Context, view : View) = with(context) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).let { inputMethodManager ->
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}