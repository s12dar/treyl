package com.lyvetech.transnature.features.feed.data.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    private val gson = Gson()
    private val type: Type = object : TypeToken<List<String?>?>() {}.type

    /**
     * Converts a listOf[String] to a [String]
     */
    @TypeConverter
    fun fromImgRefsList(list: List<String?>?): String {
        return gson.toJson(list, type)
    }

    /**
     * Converts a [String] to a listOf[String]
     */
    @TypeConverter
    fun toImgRefsList(json: String?): List<String> {
        return gson.fromJson(json, type)
    }
}