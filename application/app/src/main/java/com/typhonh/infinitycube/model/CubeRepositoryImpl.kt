package com.typhonh.infinitycube.model

import android.content.res.Resources.NotFoundException
import com.google.gson.GsonBuilder
import com.typhonh.infinitycube.model.entity.CRGB
import com.typhonh.infinitycube.model.entity.CubeState
import com.typhonh.infinitycube.model.entity.DirectionType
import com.typhonh.infinitycube.model.entity.EffectState
import com.typhonh.infinitycube.model.entity.EffectType
import com.typhonh.infinitycube.model.entity.SymmetryType
import com.typhonh.infinitycube.model.serializer.EnumSerializer
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

        val gson = GsonBuilder()
            .registerTypeAdapter(DirectionType::class.java, EnumSerializer())
            .registerTypeAdapter(EffectType::class.java, EnumSerializer())
            .registerTypeAdapter(SymmetryType::class.java, EnumSerializer())
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://$baseUrl/")
            .addConverterFactory(GsonConverterFactory.create(gson))
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
        val response = cubeApi.getEffectState().awaitResponse()
        if (response.isSuccessful) {
            return response.body() ?: EffectState.defaultEffect
        } else {
            throw NotFoundException()
        }
    }
}