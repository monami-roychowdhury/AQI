package com.example.aqi.data

import com.tinder.scarlet.Message
import com.tinder.scarlet.Stream
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow

interface AQIService {

    @Send
    fun send(message: Any) : Boolean

    @Receive
    fun receive(): Flow<List<AQIData>>

    @Receive
    fun observeWebSocketEvent(): Flow<WebSocket.Event>
}