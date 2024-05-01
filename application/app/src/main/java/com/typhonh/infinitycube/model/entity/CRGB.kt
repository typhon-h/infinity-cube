package com.typhonh.infinitycube.model.entity

import com.google.gson.annotations.SerializedName

data class CRGB (
    @SerializedName("r")
    val r: Int,

    @SerializedName("g")
    val g: Int,

    @SerializedName("b")
    val b: Int
)
