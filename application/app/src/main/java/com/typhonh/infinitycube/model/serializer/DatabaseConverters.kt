package com.typhonh.infinitycube.model.serializer

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.typhonh.infinitycube.model.entity.CRGB

class DatabaseConverters {

    @TypeConverter
    fun crgbListToJson(value: List<CRGB>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToCRGBList(value: String): List<CRGB>? {
        val type = object : TypeToken<List<CRGB>>() {}.type
        return Gson().fromJson(value, type)
    }
}