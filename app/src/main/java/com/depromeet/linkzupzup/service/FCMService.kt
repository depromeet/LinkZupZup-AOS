package com.depromeet.linkzupzup.service

import com.depromeet.linkzupzup.component.PreferencesManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.java.KoinJavaComponent

class FCMService : FirebaseMessagingService() {

    private val preference: PreferencesManager by lazy { KoinJavaComponent.get(PreferencesManager::class.java) }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        setNotification()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        preference.setFCMToken(token)
    }

    private fun setNotification(){

    }


}