package com.depromeet.linkzupzup.component

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.depromeet.linkzupzup.AppConst.AUTHORIZATION_KEY
import com.depromeet.linkzupzup.AppConst.USER_FCM_TOKEN
import com.depromeet.linkzupzup.AppConst.USER_ID_KEY

class PreferencesManager(ctx: Context, name: String = "pref") {

    private val pref : SharedPreferences = ctx.getSharedPreferences(name, Activity.MODE_PRIVATE)
    private val saver : SharedPreferences.Editor = pref.edit()

    fun getAuthorization(): String = pref.getString(AUTHORIZATION_KEY, "") ?: ""
    fun setAuthorization(token: String) {
        saver.putString(AUTHORIZATION_KEY, token)
        saver.commit()
    }

    fun getUserId(): Int = pref.getInt(USER_ID_KEY, 0)
    fun setUserId(userId: Int) {
        saver.putInt(USER_ID_KEY, userId)
        saver.commit()
    }

    fun getFCMToken(): String = pref.getString(USER_FCM_TOKEN,"") ?: ""
    fun setFCMToken(token : String){
        saver.putString(USER_FCM_TOKEN,token)
        saver.commit()
    }

    fun isLogin(): Boolean = getAuthorization().isNotEmpty()


}