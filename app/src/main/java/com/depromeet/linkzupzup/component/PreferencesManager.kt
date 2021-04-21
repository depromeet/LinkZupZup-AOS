package com.depromeet.linkzupzup.component

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.depromeet.linkzupzup.AppConst.DEVICE_TOKEN_KEY

class PreferencesManager(ctx: Context, name: String = "pref") {

    private val pref : SharedPreferences = ctx.getSharedPreferences(name, Activity.MODE_PRIVATE)
    private val saver : SharedPreferences.Editor = pref.edit()

    fun getDeviceToken(): String = pref.getString(DEVICE_TOKEN_KEY, "") ?: ""

    fun setDeviceToken(token: String?) {
        saver.putString(DEVICE_TOKEN_KEY, token)
        saver.commit()
    }

}