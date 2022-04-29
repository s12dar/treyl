package com.lyvetech.transnature.features.tracking.domain.model

import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity

data class Session(
    var timestamp: Long = 0L,
    var averageSpeed: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurnt: Int = 0,
    val id: Int? = null
) {
    fun toSessionEntity() = SessionEntity(
        timestamp,
        averageSpeed,
        distanceInMeters,
        timeInMillis,
        caloriesBurnt,
        id
    )
}