package com.depromeet.linkzupzup.component

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.depromeet.linkzupzup.AppConst.AUTHHORIZATION_KEY
import com.depromeet.linkzupzup.AppConst.USER_ID_KEY
import com.depromeet.linkzupzup.BuildConfig

class PreferencesManager(ctx: Context, name: String = "pref") {

    private val pref : SharedPreferences = ctx.getSharedPreferences(name, Activity.MODE_PRIVATE)
    private val saver : SharedPreferences.Editor = pref.edit()

    fun getAuthorization(): String = pref.getString(AUTHHORIZATION_KEY, "") ?: ""
    fun setAuthorization(token: String) {
        saver.putString(AUTHHORIZATION_KEY, token)
        saver.commit()
    }

    fun getUserId(): String = pref.getString(USER_ID_KEY, "") ?: ""
    fun setUserId(userId: String) {
        saver.putString(USER_ID_KEY, userId)
        saver.commit()
    }


}