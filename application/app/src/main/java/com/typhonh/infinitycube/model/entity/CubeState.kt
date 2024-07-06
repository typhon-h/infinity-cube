package com.typhonh.infinitycube.model.entity

import com.google.gson.annotations.SerializedName

data class CubeState(
    @SerializedName("power")
    val power: Boolean,

    @SerializedName("intensity")
    val intensity: Int
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "power" to if (power) "1" else "0",
            "intensity" to intensity.toString()
        )
    }
}
