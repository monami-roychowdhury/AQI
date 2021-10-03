package com.example.aqi.data


data class AQIData(
    val city: String,
    val aqi: Double,
    var updatedAt: Long
)
