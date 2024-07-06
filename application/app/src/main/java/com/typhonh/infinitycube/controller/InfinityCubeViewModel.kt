package com.typhonh.infinitycube.controller

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typhonh.infinitycube.dataStore
import com.typhonh.infinitycube.model.CubeRepositoryImpl
import com.typhonh.infinitycube.model.entity.CubeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class InfinityCubeViewModel(): ViewModel() {
    private val MDNS_KEY = "mdns_address"
    private val DEFAULT_MDNS = "infcub.local"

    var mdnsAddress: String by mutableStateOf(DEFAULT_MDNS)
    private var cubeRepository = CubeRepositoryImpl(mdnsAddress)


    private val _cubeState = MutableStateFlow(CubeState(false, 0))
    val cubeState: StateFlow<CubeState> get() = _cubeState

    fun initDataStore(context: Context) {
        viewModelScope.launch {
            mdnsAddress = getAddress(context)
        }
    }

    private suspend fun getAddress(context: Context): String {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(MDNS_KEY)] ?: DEFAULT_MDNS
        }.first()
    }

    fun updateAddress(context: Context, newAddress: String) {
        viewModelScope.launch {
            context.dataStore.edit { currentPreferences ->
                currentPreferences[stringPreferencesKey(MDNS_KEY)] = newAddress
            }

            mdnsAddress = getAddress(context)
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
}