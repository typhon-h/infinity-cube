package com.typhonh.infinitycube.model

import com.typhonh.infinitycube.model.entity.CubeState
import com.typhonh.infinitycube.model.entity.EffectState

interface CubeRepository {
    suspend fun getCubeState(): CubeState
    suspend fun setCubeState(state: CubeState):  CubeState

    suspend fun getEffectState(): EffectState
    suspend fun setEffectState(state: EffectState): EffectState

    fun setUrl(baseUrl: String)
}