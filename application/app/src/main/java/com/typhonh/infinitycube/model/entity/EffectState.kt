package com.typhonh.infinitycube.model.entity

import com.google.gson.annotations.SerializedName
import okhttp3.internal.toHexString
import kotlin.math.roundToInt

data class EffectState (
    @SerializedName("name")
    val name: EffectType,

    @SerializedName("speed")
    val speed: Float,

    @SerializedName("symmetry")
    val symmetry: SymmetryType,

    @SerializedName("direction")
    val direction: DirectionType,

    @SerializedName("dot_width")
    val dotWidth: Float,

    @SerializedName("dot_spacing")
    val dotSpacing: Float,

    @SerializedName("dotBlur")
    val dotBlur: Float,

    @SerializedName("motion_range")
    val motionRange: Float,

    @SerializedName("color")
    val color: List<CRGB>
) {

    fun toMap(): Map<String, String> {
        return mapOf(
            "name" to name.ordinal.toString(),
            "speed" to speed.roundToInt().toString(),
            "symmetry" to symmetry.ordinal.toString(),
            "direction" to direction.ordinal.toString(),
            "dotWidth" to dotWidth.roundToInt().toString(),
            "dotSpacing" to dotSpacing.roundToInt().toString(),
            "dotBlur" to dotBlur.roundToInt().toString(),
            "motionRange" to motionRange.roundToInt().toString(),
        ) +
        color.withIndex()
            .associate {
                (index, value) -> "color${index + 1}" to "${intToHexString(value.r)}${intToHexString(value.g)}${intToHexString(value.b)}" }
    }

    private fun intToHexString(n: Int) : String {
        val hex = n.toHexString()

        return if (hex.length < 2) {
            "0$hex"
        } else {
            hex.reversed().substring(0, 2)
        }
    }



    companion object {
        val defaultEffect =
            EffectState(
                name = EffectType.CHASE,
                speed = 220f,
                symmetry = SymmetryType.NONE,
                direction = DirectionType.FORWARD,
                dotWidth = 1f,
                dotSpacing = 1f,
                dotBlur = 1f,
                motionRange = 10f,
                color = listOf(
                    CRGB(255,0,0), CRGB(0,0,255), CRGB(0,0,255), CRGB(255,0,0))
            )
    }

}
