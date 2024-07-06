package com.typhonh.infinitycube.model.entity

import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class CubeState(
    @SerializedName("power")
    val power: Boolean,

    @SerializedName("intensity")
    val intensity: Float
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "power" to if (power) "1" else "0",
            "intensity" to intensity.roundToInt().toString()
        )
    }
}
