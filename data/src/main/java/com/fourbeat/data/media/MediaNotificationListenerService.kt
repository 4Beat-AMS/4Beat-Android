package com.fourbeat.data.media

import android.service.notification.NotificationListenerService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
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
