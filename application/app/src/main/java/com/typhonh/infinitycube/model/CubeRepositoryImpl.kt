package com.typhonh.infinitycube.model

import android.content.res.Resources.NotFoundException
import com.typhonh.infinitycube.model.entity.CRGB
import com.typhonh.infinitycube.model.entity.CubeState
import com.typhonh.infinitycube.model.entity.DirectionType
import com.typhonh.infinitycube.model.entity.EffectState
import com.typhonh.infinitycube.model.entity.EffectType
import com.typhonh.infinitycube.model.entity.SymmetryType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


class CubeRepositoryImpl(private var baseUrl: String): CubeRepository {
    private lateinit var cubeApi: InfinityCubeApi

    init {
        setUrl(baseUrl)
    }

    override fun setUrl(baseUrl: String) {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val mOkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://$baseUrl/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        cubeApi = retrofit.create(InfinityCubeApi::class.java)
    }

    override suspend fun getCubeState(): CubeState {
        val response = cubeApi.getCubeState().awaitResponse()
        if (response.isSuccessful) {
            return response.body() ?: CubeState(false, 0f)
        } else {
            throw NotFoundException()
        }
    }

    override suspend fun setCubeState(state: CubeState): CubeState {
        val response = cubeApi.setCubeState(state.toMap()).awaitResponse()
        if (response.isSuccessful) {
            return response.body() ?: state
        } else {
            throw NotFoundException()
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

        val response = cubeApi.getEffectState().awaitResponse()
        if (response.isSuccessful) {
            return response.body() ?: defaultEffect
        } else {
            throw NotFoundException()
        }
    }
}