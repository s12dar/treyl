package com.lyvetech.transnature.features.feed.domain.model

data class Trail(
    val name: String,
    val desc: String,
    val imgUrl: String?,
    val startLatitude: Double,
    val startLongitude: Double,
    val endLatitude: Double,
    val endLongitude: Double,
    val distanceInMeters: Int,
    val averageTimeInMillis: Long,
)