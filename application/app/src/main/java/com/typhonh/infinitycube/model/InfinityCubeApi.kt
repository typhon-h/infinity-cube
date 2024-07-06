package com.typhonh.infinitycube.model

import com.typhonh.infinitycube.model.entity.CubeState
import com.typhonh.infinitycube.model.entity.EffectState
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface InfinityCubeApi {
    @GET("led")
    fun getCubeState(): Call<CubeState>

    @PUT("led")
    fun setCubeState(@Body state: CubeState): Call<CubeState>

    @GET("led/effect")
    fun getEffectState(): Call<EffectState>
}