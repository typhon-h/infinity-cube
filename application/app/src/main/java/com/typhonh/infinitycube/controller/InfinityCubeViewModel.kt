package com.typhonh.infinitycube.controller

import android.content.Context
import android.content.res.Resources.NotFoundException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typhonh.infinitycube.model.CubeRepository
import com.typhonh.infinitycube.model.CubeRepositoryImpl
import com.typhonh.infinitycube.model.MdnsManager
import com.typhonh.infinitycube.model.entity.CubeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class InfinityCubeViewModel(): ViewModel() {
    val INTENSITY_CAP = 254f // Not 255

    private val mdnsManager = MdnsManager()

    private var cubeRepository: CubeRepository = CubeRepositoryImpl(mdnsManager.DEFAULT_MDNS)

    private val _cubeState = MutableStateFlow(CubeState(false, 0f))
    val cubeState: StateFlow<CubeState> get() = _cubeState

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    fun init(context: Context) {
        viewModelScope.launch {
            mdnsManager.init(context)
            cubeRepository.setUrl(mdnsManager.resolveAddress(mdnsManager.mdnsAddress))
            update()
        }
    }

    fun getAddress(): String {
        return mdnsManager.mdnsAddress
    }

    fun setAddress(context: Context, newAddress: String) {
        viewModelScope.launch {
            mdnsManager.updateAddress(context, newAddress)
            cubeRepository.setUrl(mdnsManager.resolveAddress(mdnsManager.mdnsAddress))
        }
    }

    private fun repositoryWrapper(func: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                func()
                _isConnected.value = true
            } catch (e: Exception) {
                when (e) {
                    is NotFoundException,
                    is UnknownHostException -> {
                        _isConnected.value = false
                    }
                    else -> throw e
                }

            }
        }
    }

    fun update() {
        repositoryWrapper {
            _cubeState.value = cubeRepository.getCubeState()
        }
        //TODO: Update effect params here
    }

    fun setPower(isOn: Boolean) {
        repositoryWrapper {
            _cubeState.value = cubeRepository.setCubeState(
                CubeState(isOn, _cubeState.value.intensity)
            )
        }
    }

    fun setIntensity(newIntensity: Float) {
        repositoryWrapper {
            _cubeState.value = cubeRepository.setCubeState(
                CubeState(true, newIntensity)
            )
        }
    }
}