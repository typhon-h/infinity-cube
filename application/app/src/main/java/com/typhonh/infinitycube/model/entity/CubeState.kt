package com.typhonh.infinitycube.model.entity

import com.google.gson.annotations.SerializedName

data class CubeState(
    @SerializedName("power")
    val power: Boolean,

    @SerializedName("intensity")
    val intensity: Int
)
