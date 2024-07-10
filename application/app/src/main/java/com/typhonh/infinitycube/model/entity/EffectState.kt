package com.typhonh.infinitycube.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import okhttp3.internal.toHexString
import kotlin.math.roundToInt

@Entity
data class EffectState (
    @ColumnInfo
    @SerializedName("name")
    val name: EffectType,

    @ColumnInfo
    @SerializedName("speed")
    val speed: Float,

    @ColumnInfo
    @SerializedName("symmetry")
    val symmetry: SymmetryType,

    @ColumnInfo
    @SerializedName("direction")
    val direction: DirectionType,

    @ColumnInfo
    @SerializedName("dot_width")
    val dotWidth: Float,

    @ColumnInfo
    @SerializedName("dot_spacing")
    val dotSpacing: Float,

    @ColumnInfo
    @SerializedName("dotBlur")
    val dotBlur: Float,

    @ColumnInfo
    @SerializedName("motion_range")
    val motionRange: Float,

    @ColumnInfo
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
