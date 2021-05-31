package com.depromeet.linkzupzup.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmSettingBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            /**
             * 기본적으로 기기를 종료하면 모든 알람이 취소됩니다.
             * 이런 상황을 방지하기 위해 디바이스가 부팅되었을 시,
             * 현재 이곳에서 알림을 다시 세팅해야됩니다.
             *
             * SQLite에서 설정된 알림들을 불러와서 다시 재등록해야됨.
             */


        }
    }
}