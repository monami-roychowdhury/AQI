package com.example.aqi.di

import android.app.Application
import com.example.aqi.data.AQICoroutinesStreamAdapterFactory
import com.example.aqi.data.AQIService
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.StreamAdapter
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

const val SERVER_URL = "ws://city-ws.herokuapp.com/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val httpClient = OkHttpClient.Builder()
    private val okHttpClient = httpClient.build()


    @Provides
    @Singleton
    fun provideScarlet(application: Application): Scarlet = Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory(SERVER_URL))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(AQICoroutinesStreamAdapterFactory())
        .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
        .build()


    @Provides
    @Singleton
    fun provideApi(scarlet: Scarlet): AQIService =
        scarlet.create(AQIService::class.java)
}