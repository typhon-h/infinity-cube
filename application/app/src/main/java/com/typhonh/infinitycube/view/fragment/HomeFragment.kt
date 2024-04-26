package com.typhonh.infinitycube.view.fragment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.typhonh.infinitycube.R
import com.typhonh.infinitycube.view.MainScaffold
import com.typhonh.infinitycube.view.composable.sheet.ColorSettingsSheet
import com.typhonh.infinitycube.view.composable.sheet.EffectSettingsSheet
import com.typhonh.infinitycube.view.composable.IntensitySlider
import com.typhonh.infinitycube.view.composable.ModalToggle
import com.typhonh.infinitycube.view.composable.PowerToggle
import com.typhonh.infinitycube.view.composable.sheet.PresetSettingsSheet

@Composable
fun HomeFragment() {
    var showColorSheet by remember { mutableStateOf(false) }
    var showEffectSheet by remember { mutableStateOf(false) }
    var showPresetSheet by remember { mutableStateOf(false) }

    MainScaffold() {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.weight(0.5f), verticalArrangement = Arrangement.Center) {
                PowerToggle() //TODO: Send current percentage here to display instead of "Off"
            }
            Column(modifier = Modifier.weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ModalToggle(icon = painterResource(id = R.drawable.palette), text = "Color", description = "Change Color Palette") {
                        showColorSheet = true
                    }
                    ModalToggle(icon = painterResource(id = R.drawable.lens_blur), text = "Effect", description = "Change Current Effect") {
                        showEffectSheet = true
                    }
                    ModalToggle(icon = painterResource(id = R.drawable.storage), text = "Preset", description = "Save or Load Preset") {
                        showPresetSheet = true
                    }
                }
                IntensitySlider()
            }
        }

        ColorSettingsSheet(showColorSheet, onDismissRequest = {showColorSheet = false})
        EffectSettingsSheet(showEffectSheet, onDismissRequest = {showEffectSheet = false})
        PresetSettingsSheet(showPresetSheet, onDismissRequest = {showPresetSheet = false})
    }

}