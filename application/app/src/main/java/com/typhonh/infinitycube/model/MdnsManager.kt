package com.typhonh.infinitycube.model

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.typhonh.infinitycube.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MdnsManager {
    private val MDNS_KEY = "mdns_address"
    private val DEFAULT_MDNS = "infcub.local"

    private var _mdnsAddress: String by mutableStateOf(DEFAULT_MDNS)
    val mdnsAddress: String
        get() = _mdnsAddress

    suspend fun init(context: Context) {
        _mdnsAddress = getAddress(context)
    }

    private suspend fun getAddress(context: Context): String {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(MDNS_KEY)] ?: DEFAULT_MDNS
        }.first()
    }

    suspend fun updateAddress(context: Context, newAddress: String) {
            context.dataStore.edit { currentPreferences ->
                currentPreferences[stringPreferencesKey(MDNS_KEY)] = newAddress
            }

            _mdnsAddress = getAddress(context)
    }
}