package com.typhonh.infinitycube.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
fun SpeedSlider() {
    val sliderState by remember { mutableStateOf(SliderState(value = 0.5f)) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Icon(painter = painterResource(R.drawable.bolt_hollow), //TODO: Make these buttons to snap to off/full
                contentDescription = "Brightness Down",
                modifier = Modifier.weight(0.1f)
            )

            Slider(
                state = sliderState,
                modifier = Modifier.weight(0.8f)
            )

            Icon(painter = painterResource(R.drawable.bolt_fill),
                contentDescription = "Brightness Up",
                modifier = Modifier.weight(0.1f)
            )

        }

        Text("${(sliderState.value * 100).toInt()}%", style = MaterialTheme.typography.titleLarge)
    }
}