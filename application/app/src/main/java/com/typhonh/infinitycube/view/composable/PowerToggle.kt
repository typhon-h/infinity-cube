package com.typhonh.infinitycube.view.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.typhonh.infinitycube.R

@Composable
fun PowerToggle(isOn: Boolean, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(200.dp)
            .border(Dp.Hairline, if (isOn) Color.Green else MaterialTheme.colorScheme.outline, CircleShape)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = R.drawable.power_settings_new),
                contentDescription = "Toggle Power",
                modifier = Modifier.fillMaxSize(0.30f)
            )

            Text(if (isOn) "On" else "Off",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(0.dp,75.dp,0.dp,0.dp))
        }
    }
}