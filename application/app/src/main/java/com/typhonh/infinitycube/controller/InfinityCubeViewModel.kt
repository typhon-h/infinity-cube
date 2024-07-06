package com.typhonh.infinitycube.controller

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typhonh.infinitycube.model.CubeRepository
import com.typhonh.infinitycube.model.CubeRepositoryImpl
import com.typhonh.infinitycube.model.MdnsManager
import com.typhonh.infinitycube.model.entity.CubeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InfinityCubeViewModel(): ViewModel() {
    val INTENSITY_CAP = 254f // Not 255

    private val mdnsManager = MdnsManager()

    private var cubeRepository: CubeRepository = CubeRepositoryImpl(mdnsManager.DEFAULT_MDNS)

    private val _cubeState = MutableStateFlow(CubeState(false, 0f))
    val cubeState: StateFlow<CubeState> get() = _cubeState

    fun init(context: Context) {
        viewModelScope.launch {
            mdnsManager.init(context)
            cubeRepository.setUrl(mdnsManager.resolveAddress(mdnsManager.mdnsAddress))
            getCubeState()
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

    fun getCubeState() {
        viewModelScope.launch {
            _cubeState.value = cubeRepository.getCubeState()
        }
    }

    fun setPower(isOn: Boolean) {
        viewModelScope.launch {
            _cubeState.value = cubeRepository.setCubeState(
                CubeState(isOn, _cubeState.value.intensity)
            )
        }
    }

    fun setIntensity(newIntensity: Float) {
        viewModelScope.launch {
            _cubeState.value = cubeRepository.setCubeState(
                CubeState(true, newIntensity)
            )
        }
    }
}