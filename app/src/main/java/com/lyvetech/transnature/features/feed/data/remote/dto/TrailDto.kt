package com.lyvetech.transnature.features.feed.data.remote.dto

import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity

data class TrailDto(
    val name: String,
    val desc: String,
    val imgRefs: List<String>,
    val location: String,
    val startLatitude: Double,
    val startLongitude: Double,
    val endLatitude: Double,
    val endLongitude: Double,
    val distanceInMeters: Int,
    val peakPointInMeters: Int,
    val averageTimeInMillis: Long,
    val difficultyLevel: String,
    val accession: String,
    val warning: String
) {
    fun toTrailEntity(): TrailEntity {
        return TrailEntity(
            name = name, desc = desc, location = location,
            imgRefs = imgRefs, startLatitude = startLatitude,
            startLongitude = startLongitude, endLatitude = endLatitude,
            endLongitude = endLongitude, distanceInMeters = distanceInMeters,
            peakPointInMeters = peakPointInMeters, averageTimeInMillis = averageTimeInMillis,
            difficultyLevel = difficultyLevel, accession = accession, warning = warning
        )
    }
}
