package com.typhonh.infinitycube.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.typhonh.infinitycube.R

@Composable
fun IntensitySlider(defaultValue: Float, valueRange: ClosedFloatingPointRange<Float>, onValueChangeFinished: (Float) -> Unit) {
    var value by remember(defaultValue) { mutableFloatStateOf(defaultValue) }


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            IconButton(
                modifier = Modifier.weight(0.1f),
                onClick = { onValueChangeFinished(valueRange.start) })
            {
                Icon(painter = painterResource(R.drawable.brightness_1),
                    contentDescription = "Brightness Down",
                )
            }

            Slider(
                value = value,
                onValueChange = { value = it  },
                onValueChangeFinished = { onValueChangeFinished(value) },
                valueRange = valueRange,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = (value/valueRange.endInclusive)),
                    inactiveTrackColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.weight(0.8f)
            )

            IconButton(
                modifier = Modifier.weight(0.1f),
                onClick = { onValueChangeFinished(valueRange.endInclusive) })
            {
                Icon(
                    painter = painterResource(R.drawable.brightness_7),
                    contentDescription = "Brightness Up",
                )
            }

        }

        Text("${((value/valueRange.endInclusive) * 100).toInt()}%", style = MaterialTheme.typography.titleLarge)
    }
}