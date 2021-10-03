package com.example.aqi.data

import com.tinder.scarlet.StreamAdapter
import com.tinder.scarlet.utils.getRawType
import kotlinx.coroutines.flow.Flow
import java.lang.reflect.Type

class AQICoroutinesStreamAdapterFactory : StreamAdapter.Factory{


    override fun create(type: Type): StreamAdapter<Any, Any> {
        return when (type.getRawType()) {
            Flow::class.java -> ReceiveChannelStreamAdapter()
            else -> throw IllegalArgumentException()
        }
    }
}