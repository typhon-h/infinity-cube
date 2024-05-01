package com.typhonh.infinitycube.model

import com.typhonh.infinitycube.model.entity.CubeState
import com.typhonh.infinitycube.model.entity.EffectState
import retrofit2.Call
import retrofit2.http.GET

interface InfinityCubeApi {
    @GET("led")
    fun getCubeState(): Call<CubeState>

    @GET("led/effect")
    fun getEffectState(): Call<EffectState>
}