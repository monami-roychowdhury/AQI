package com.example.aqi.data

import androidx.lifecycle.*
import com.example.aqi.R
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AQIViewModel @Inject constructor(private val aqiService: AQIService) : ViewModel() {


    fun receive(): Flow<List<AQIData>> {
        return aqiService.receive()
    }

    fun observeWebSocketEvent(): Flow<WebSocket.Event> {
        return aqiService.observeWebSocketEvent()
    }
}