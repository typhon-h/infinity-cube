package com.typhonh.infinitycube.controller

import android.content.Context
import android.content.res.Resources.NotFoundException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typhonh.infinitycube.model.CubeRepository
import com.typhonh.infinitycube.model.CubeRepositoryImpl
import com.typhonh.infinitycube.model.MdnsManager
import com.typhonh.infinitycube.model.entity.CubeState
import com.typhonh.infinitycube.model.entity.EffectState
import kotlinx.coroutines.delay
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

    private val _effectState = MutableStateFlow(EffectState.defaultEffect)
    val effectState: StateFlow<EffectState> get() = _effectState

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected
    private val _isConnecting = MutableStateFlow(true)
    val isConnecting: StateFlow<Boolean> get() = _isConnecting

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
            val retryCounter = 5
            var isSuccess = false
            for(i in 0..retryCounter) {
                try {
                    func()
                    isSuccess = true
                    break
                } catch (e: Exception) {
                    isSuccess = false
                    when (e) {
                        is NotFoundException,
                        is UnknownHostException -> {
                            _isConnected.value = false
                        }
                        else -> throw e
                    }
                }
                delay(500)
            }

            _isConnected.value = isSuccess
            _isConnecting.value = false
        }
    }

    fun update() {
        viewModelScope.launch { _isConnecting.value = true }
        repositoryWrapper { _cubeState.value = cubeRepository.getCubeState() }
        repositoryWrapper { _effectState.value = cubeRepository.getEffectState() }
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