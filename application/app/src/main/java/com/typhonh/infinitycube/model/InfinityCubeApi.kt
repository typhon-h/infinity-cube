package com.typhonh.infinitycube.model

import com.typhonh.infinitycube.model.entity.CubeState
import com.typhonh.infinitycube.model.entity.EffectState
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface InfinityCubeApi {
    @GET("led")
    fun getCubeState(): Call<CubeState>

    @PUT("led")
    fun setCubeState(@QueryMap options: Map<String, String>): Call<CubeState>

    @GET("led/effect")
    fun getEffectState(): Call<EffectState>

    @PUT("led/effect")
    fun setEffectState(@QueryMap options: Map<String, String>): Call<EffectState>

    @POST("connect")
    fun setWifi(@Query("SSID") ssid: String, @Query("PASSWORD") password: String): Call<String>

    @GET("backdoor/reset")
    fun factoryReset(): Call<String>
}