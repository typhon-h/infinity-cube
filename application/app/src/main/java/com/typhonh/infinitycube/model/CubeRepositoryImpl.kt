package com.typhonh.infinitycube.model

import com.typhonh.infinitycube.model.entity.CRGB
import com.typhonh.infinitycube.model.entity.CubeState
import com.typhonh.infinitycube.model.entity.DirectionType
import com.typhonh.infinitycube.model.entity.EffectState
import com.typhonh.infinitycube.model.entity.EffectType
import com.typhonh.infinitycube.model.entity.SymmetryType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class CubeRepositoryImpl(private var baseUrl: String): CubeRepository {
    private val cubeApi: InfinityCubeApi

    init {
         val mOkHttpClient = OkHttpClient
            .Builder()
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://$baseUrl/") //TODO: Check updating mdns works
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        cubeApi = retrofit.create(InfinityCubeApi::class.java)
    }
    override suspend fun getCubeState(): CubeState {
        return try {
            val response = cubeApi.getCubeState().awaitResponse()
            if (response.isSuccessful) {
                response.body() ?: CubeState(false, 0)
            } else {
                CubeState(false, 0)
            }
        } catch (exception: Exception) {
            CubeState(false, 0)
        }
    }

    override suspend fun getEffectState(): EffectState {
        val defaultEffect = EffectState(
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

        return try {
            val response = cubeApi.getEffectState().awaitResponse()
            if (response.isSuccessful) {
                response.body() ?: defaultEffect
            } else {
                defaultEffect
            }
        } catch (exception: Exception) {
            defaultEffect
        }
    }
}