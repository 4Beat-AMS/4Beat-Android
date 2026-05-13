package com.fourbeat.presentation.model.common

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

object MessageCollector {
    private val messageChannel = Channel<String>(capacity = Channel.BUFFERED)
    val messageFlow: Flow<String> = messageChannel.receiveAsFlow()

    suspend fun sendMessage(message: String) {
        messageChannel.send(message)
    }
}