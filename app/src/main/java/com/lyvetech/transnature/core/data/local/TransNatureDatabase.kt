package com.lyvetech.transnature.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lyvetech.transnature.features.feed.data.local.dao.FeedDao
import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity
import com.lyvetech.transnature.features.feed.data.util.Converters

@Database(
    entities = [TrailEntity::class],
    version = 7
)
@TypeConverters(
    Converters::class
)
abstract class TransNatureDatabase : RoomDatabase() {
    abstract val dao: FeedDao
}