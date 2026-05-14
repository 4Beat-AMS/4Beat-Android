package com.fourbeat.data.datasource.media

import kotlinx.coroutines.flow.Flow

interface NotificationListenerPermissionDataSource {
    fun getPermissionFlow(): Flow<Boolean>
}
