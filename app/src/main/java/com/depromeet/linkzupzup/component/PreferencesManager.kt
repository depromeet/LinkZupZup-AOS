package com.depromeet.linkzupzup.component

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.depromeet.linkzupzup.AppConst.AUTHORIZATION_KEY
import com.depromeet.linkzupzup.AppConst.LOGIN_ID
import com.depromeet.linkzupzup.AppConst.PUSH_AGREE_KEY
import com.depromeet.linkzupzup.AppConst.TODAY_READ_KEY
import com.depromeet.linkzupzup.AppConst.USER_FCM_TOKEN
import com.depromeet.linkzupzup.AppConst.USER_ID_KEY
import com.depromeet.linkzupzup.AppConst.USER_NAME_KEY

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

    fun getLoginId(): Long = pref.getLong(LOGIN_ID, 0)
    fun setLoginId(loginId: Long) {
        saver.putLong(LOGIN_ID, loginId)
        saver.commit()
    }

    fun getUserName(): String = pref.getString(USER_NAME_KEY, "") ?: ""
    fun setUserName(userName: String) {
        saver.putString(USER_NAME_KEY, userName)
        saver.commit()
    }

    fun getFCMToken(): String = pref.getString(USER_FCM_TOKEN,"") ?: ""
    fun setFCMToken(token : String){
        saver.putString(USER_FCM_TOKEN,token)
        saver.commit()
    }

    fun isLogin(): Boolean = getAuthorization().isNotEmpty()
    fun logout() {
        saver.remove(AUTHORIZATION_KEY)
        saver.remove(USER_ID_KEY)
        saver.remove(LOGIN_ID)
        saver.commit()
    }

    fun getTodayCount(): Int = pref.getInt(TODAY_READ_KEY, 0)
    fun setTodayCount(cnt: Int) {
        saver.putInt(TODAY_READ_KEY, cnt)
        saver.commit()
    }

    fun getPushAgree(): Boolean = pref.getBoolean(PUSH_AGREE_KEY, true)
    fun setPushAgree(agree: Boolean) {
        saver.putBoolean(PUSH_AGREE_KEY, agree)
        saver.commit()
    }


}