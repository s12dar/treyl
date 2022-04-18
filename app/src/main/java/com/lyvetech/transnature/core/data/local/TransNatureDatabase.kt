package com.lyvetech.transnature.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lyvetech.transnature.features.feed.data.local.FeedDao
import com.lyvetech.transnature.features.feed.data.local.entity.TrailEntity

@Database(
    entities = [TrailEntity::class],
    version = 1
)
abstract class TransNatureDatabase : RoomDatabase() {
    abstract val dao: FeedDao
}