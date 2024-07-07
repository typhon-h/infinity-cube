package com.typhonh.infinitycube.model.entity

import com.google.gson.annotations.SerializedName

data class EffectState (
    @SerializedName("name")
    val name: EffectType,

    @SerializedName("speed")
    val speed: Int,

    @SerializedName("symmetry")
    val symmetry: SymmetryType,

    @SerializedName("direction")
    val direction: DirectionType,

    @SerializedName("dot_width")
    val dotWidth: Int,

    @SerializedName("dot_spacing")
    val dotSpacing: Int,

    @SerializedName("dotBlur")
    val dotBlur: Int,

    @SerializedName("motion_range")
    val motionRange: Int,

    @SerializedName("color")
    val color: List<CRGB>
) {




    companion object {
        val defaultEffect =
            EffectState(
                name = EffectType.CHASE,
                speed = 220,
                symmetry = SymmetryType.NONE,
                direction = DirectionType.FORWARD,
                dotWidth = 1,
                dotSpacing = 1,
                dotBlur = 1,
                motionRange = 10,
                color = listOf(CRGB(255,0,0),CRGB(0,0,255))
            )
    }

}
