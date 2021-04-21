package com.depromeet.linkzupzup.component

import android.app.Activity
import android.widget.Toast
import com.depromeet.linkzupzup.R

class BackPressCloseHandler(private val activity: Activity) {

    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null
    fun onBackPressed() {
        var curTime = System.currentTimeMillis()
        if (curTime > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
            return
        }
        if (curTime <= backKeyPressedTime + 2000) {
            activity.finish()
            toast?.cancel()
        }
    }

    private fun showGuide() {
        Toast.makeText(activity, activity.getString(R.string.back_press_str), Toast.LENGTH_SHORT)
            .also { toast = it }
            .show()
    }

}
