package com.typhonh.infinitycube.model.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preset")
data class Preset(
    @PrimaryKey(autoGenerate = true) val presetId: Long = 0,
    @ColumnInfo val presetName: String,
    @Embedded val state: CubeState,
    @Embedded val effect: EffectState,
)
