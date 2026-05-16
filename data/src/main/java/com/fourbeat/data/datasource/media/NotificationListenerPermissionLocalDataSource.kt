package com.fourbeat.data.datasource.media

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NotificationListenerPermissionLocalDataSource(
    private val context: Context,
) : NotificationListenerPermissionDataSource {

    override fun getPermissionFlow(): Flow<Boolean> = callbackFlow {
        val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                trySend(isGranted())
            }
        }
        context.contentResolver.registerContentObserver(
            Settings.Secure.getUriFor(ENABLED_NOTIFICATION_LISTENERS),
            true,
            observer,
        )
        trySend(isGranted())
        awaitClose { context.contentResolver.unregisterContentObserver(observer) }
    }.distinctUntilChanged()

    private fun isGranted(): Boolean =
        NotificationManagerCompat.getEnabledListenerPackages(context)
            .contains(context.packageName)

    companion object {
        private const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
    }
}
