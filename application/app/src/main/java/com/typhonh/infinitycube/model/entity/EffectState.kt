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
)
