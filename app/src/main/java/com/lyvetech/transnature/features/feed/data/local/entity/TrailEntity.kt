package com.lyvetech.transnature.features.feed.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lyvetech.transnature.features.feed.data.remote.dto.TrailDto
import com.lyvetech.transnature.features.feed.domain.model.Trail

@Entity
data class TrailEntity(
    val name: String,
    val desc: String,
    val imgUrl: String?,
    val startLatitude: Double,
    val startLongitude: Double,
    val endLatitude: Double,
    val endLongitude: Double,
    val distanceInMeters: Int,
    val averageTimeInMillis: Long,
    @PrimaryKey val id: Int? = null
) {
    fun toTrail() = Trail(
        name,
        desc,
        imgUrl,
        startLatitude,
        startLongitude,
        endLatitude,
        endLongitude,
        distanceInMeters,
        averageTimeInMillis
    )
}
