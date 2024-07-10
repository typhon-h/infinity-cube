package com.typhonh.infinitycube.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CRGB (
    @SerializedName("r")
    @ColumnInfo
    val r: Int,

    @SerializedName("g")
    @ColumnInfo
    val g: Int,

    @SerializedName("b")
    @ColumnInfo
    val b: Int,

    @PrimaryKey
    val crgbId: Long = 0,
)
