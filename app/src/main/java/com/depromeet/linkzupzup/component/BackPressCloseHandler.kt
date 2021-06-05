package com.depromeet.linkzupzup.component

import android.app.Activity
import android.widget.Toast
import com.depromeet.linkzupzup.R
import com.depromeet.linkzupzup.extensions.toast

class BackPressCloseHandler(private val activity: Activity) {
    companion object {
        const val WAIT_TIMEMILLIS: Long = 2000
    }

    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null
    fun onBackPressed() {
        System.currentTimeMillis().let { curTimeMillis ->
            if (curTimeMillis > backKeyPressedTime + WAIT_TIMEMILLIS) {
                backKeyPressedTime = System.currentTimeMillis()
                showGuide()
                return@onBackPressed
            }
            if (curTimeMillis <= backKeyPressedTime + WAIT_TIMEMILLIS) {
                activity.finish()
                toast?.cancel()
            }
        }
    }

    private fun showGuide() = with(activity) {
        toast(this, getString(R.string.back_press_str), Toast.LENGTH_SHORT).also { toast = it }
    }

}
