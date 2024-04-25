package com.typhonh.infinitycube.view.fragment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.typhonh.infinitycube.R
import com.typhonh.infinitycube.view.MainScaffold
import com.typhonh.infinitycube.view.composable.IntensitySlider
import com.typhonh.infinitycube.view.composable.ModalToggle
import com.typhonh.infinitycube.view.composable.PowerToggle

@Composable
fun HomeFragment() {
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
                    ModalToggle(icon = painterResource(id = R.drawable.palette), text = "Color", description = "Change Color Palette")
                    ModalToggle(icon = painterResource(id = R.drawable.lens_blur), text = "Effect", description = "Change Current Effect")
                    ModalToggle(icon = painterResource(id = R.drawable.storage), text = "Preset", description = "Save or Load Preset")
                }

                IntensitySlider()

            }
        }

    }

}