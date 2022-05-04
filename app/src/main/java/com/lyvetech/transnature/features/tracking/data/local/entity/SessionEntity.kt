package com.lyvetech.transnature.features.tracking.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lyvetech.transnature.features.tracking.domain.model.Session

@Entity(tableName = "session_table")
data class SessionEntity(
    var title: String = "",
    var timestamp: Long = 0L, // when the run was
    var averageSpeed: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L, // how long the run was
    var caloriesBurnt: Int = 0,
    @PrimaryKey val id: Int? = null
) {
    fun toSession() = Session(
        title,
        timestamp,
        averageSpeed,
        distanceInMeters,
        timeInMillis,
        caloriesBurnt,
        id
    )
}
