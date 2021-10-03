package com.example.aqi.data

import com.tinder.scarlet.Stream
import com.tinder.scarlet.StreamAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.collect


class ReceiveChannelStreamAdapter<T> : StreamAdapter<T, Flow<T>> {

    override fun adapt(stream: Stream<T>) = flow {
        stream.collect {
            emit(it)
        }
    }


}