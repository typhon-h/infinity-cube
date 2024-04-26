package com.typhonh.infinitycube.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DotWidthSlider() {
    val sliderState by remember { mutableStateOf(SliderState(value = 2f, steps = 15, valueRange = 0f..15f)) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Slider(
            state = sliderState
        )

        Text("${(sliderState.value).toInt()}", style = MaterialTheme.typography.titleLarge)
    }
}