package com.typhonh.infinitycube.model.entity

import com.google.gson.annotations.SerializedName

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
                color = listOf(CRGB(255,0,0),CRGB(0,0,255))
            )
    }

}
