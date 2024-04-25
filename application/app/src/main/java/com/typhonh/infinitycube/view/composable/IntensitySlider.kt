package com.typhonh.infinitycube.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.typhonh.infinitycube.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntensitySlider() {
    val sliderState by remember { mutableStateOf(SliderState(value = 0.5f)) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Icon(painter = painterResource(R.drawable.brightness_1), //TODO: Make these buttons to snap to off/full
                contentDescription = "Brightness Down",
                modifier = Modifier.weight(0.1f)
            )

            Slider(
                state = sliderState,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = sliderState.value),
                    inactiveTrackColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.weight(0.8f)
            )

            Icon(painter = painterResource(R.drawable.brightness_7),
                contentDescription = "Brightness Up",
                modifier = Modifier.weight(0.1f)
            )

        }

        Text("${(sliderState.value * 100).toInt()}%", style = MaterialTheme.typography.titleLarge)
    }
}