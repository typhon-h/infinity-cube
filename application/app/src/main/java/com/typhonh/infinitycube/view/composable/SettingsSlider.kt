package com.typhonh.infinitycube.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun SettingsSlider(
    defaultValue: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    showPercentage: Boolean = false,
    showValue: Boolean = false,
    transparentSlider: Boolean = false,
    steps: Int = 0,
    minButton: @Composable (modifier: Modifier) -> Unit = {},
    maxButton: @Composable (modifier: Modifier) -> Unit = {},
    onValueChangeFinished: (Float) -> Unit
) {
    var value by remember(defaultValue) { mutableFloatStateOf(defaultValue) }


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){

            minButton(Modifier.weight(0.1f))

            Slider(
                value = value,
                onValueChange = { value = it  },
                onValueChangeFinished = { onValueChangeFinished(value) },
                valueRange = valueRange,
                steps = steps,
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = if (transparentSlider) value/valueRange.endInclusive else 1f),
                    inactiveTrackColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.weight(0.8f)
            )

            maxButton(Modifier.weight(0.1f),)
        }

        if(showPercentage || showValue) {
            Text(
                "${
                    if (showPercentage) {
                        "${((value / valueRange.endInclusive) * 100).toInt()}%"
                    } else (
                        value.toInt()
                    )
                }",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}