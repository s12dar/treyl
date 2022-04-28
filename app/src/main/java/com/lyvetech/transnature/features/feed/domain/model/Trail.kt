package com.lyvetech.transnature.features.feed.domain.model

import com.google.android.gms.maps.model.LatLng
import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity
import java.io.Serializable

data class Trail(
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
    var isFav: Boolean,
    val tag: String,
    var route: List<LatLng>? = null,
    val uid: Int?,
) : Serializable {

    fun toTrailEntity() = TrailEntity(
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
        tag,
        uid
    )
}