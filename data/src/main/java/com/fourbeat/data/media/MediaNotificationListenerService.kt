package com.fourbeat.data.media

import android.service.notification.NotificationListenerService
import timber.log.Timber

class MediaNotificationListenerService : NotificationListenerService() {
    override fun onListenerConnected() {
        super.onListenerConnected()
        Timber.tag("미디어 리스너").i("연결 시작")
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Timber.tag("미디어 리스너").i("연결 해제")
    }
}
