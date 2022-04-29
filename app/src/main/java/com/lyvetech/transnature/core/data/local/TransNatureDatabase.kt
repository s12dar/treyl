package com.lyvetech.transnature.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lyvetech.transnature.features.feed.data.local.dao.FeedDao
import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity
import com.lyvetech.transnature.features.feed.data.util.Converters
import com.lyvetech.transnature.features.tracking.data.local.dao.TrackDao
import com.lyvetech.transnature.features.tracking.data.local.entity.SessionEntity

@Database(
    entities = [TrailEntity::class, SessionEntity::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(
    Converters::class
)
abstract class TransNatureDatabase : RoomDatabase() {

    abstract val feedDao: FeedDao

    abstract val trackDao: TrackDao
}