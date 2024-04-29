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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class InfinityCubeViewModel(): ViewModel() {
    private val MDNS_KEY = "mdns_address"
    private val DEFAULT_MDNS = "infcub.local"


    var mdns_address: String by mutableStateOf(DEFAULT_MDNS)

    fun initDataStore(context: Context) {
        viewModelScope.launch {
            mdns_address = getAddress(context)
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

            mdns_address = getAddress(context)
        }
    }
}