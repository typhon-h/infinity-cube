package com.typhonh.infinitycube.controller

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typhonh.infinitycube.model.CubeRepositoryImpl
import com.typhonh.infinitycube.model.MdnsManager
import com.typhonh.infinitycube.model.entity.CubeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InfinityCubeViewModel(): ViewModel() {
    private val mdnsManager = MdnsManager()
    val mdnsAddress: String get() = mdnsManager.mdnsAddress

    private var cubeRepository = CubeRepositoryImpl(mdnsManager.mdnsAddress)

    private val _cubeState = MutableStateFlow(CubeState(false, 0))
    val cubeState: StateFlow<CubeState> get() = _cubeState

    fun init(context: Context) {
        viewModelScope.launch {
            mdnsManager.init(context)
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

    fun setAddress(context: Context, newAddress: String) {
        viewModelScope.launch {
            mdnsManager.updateAddress(context, newAddress)
            //TODO: Probably needs to resolve the new mdns + update the repository
        }
    }
}