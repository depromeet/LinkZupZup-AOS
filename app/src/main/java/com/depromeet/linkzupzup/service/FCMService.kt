package com.depromeet.linkzupzup.service

import com.depromeet.linkzupzup.component.PreferencesManager
import com.depromeet.linkzupzup.utils.DLog
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.java.KoinJavaComponent
import com.depromeet.linkzupzup.receiver.NotifiChannel
import com.depromeet.linkzupzup.receiver.NotifiItem
import com.depromeet.linkzupzup.receiver.NotificationSetting.deliverNotification

class FCMService : FirebaseMessagingService() {

    private val preference: PreferencesManager by lazy { KoinJavaComponent.get(PreferencesManager::class.java) }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        showNotification(remoteMessage)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        preference.setFCMToken(token)
    }

    private fun showNotification(remoteMessage: RemoteMessage){
        remoteMessage.data.let{
            DLog.d("FCM RemoteMessage","${it["title"]} ${it["body"]}")

            deliverNotification(applicationContext,NotifiItem(
                it["title"].toString(),
                it["body"].toString(),
                NotifiChannel.STATISTICS
            ))
        }

    }


}