package com.lyvetech.transnature.features.feed.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lyvetech.transnature.features.feed.domain.model.Trail

@Entity
data class TrailEntity(
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
    val warning: String,
    val isFav: Boolean = false,
    @PrimaryKey val id: Int? = null
) {
    fun toTrail() = Trail(
        name,
        desc,
        imgRefs,
        location,
        startLatitude,
        startLongitude,
        endLatitude,
        endLongitude,
        distanceInMeters,
        peakPointInMeters,
        averageTimeInMillis,
        difficultyLevel,
        accession,
        warning,
        isFav,
        id
    )
}
