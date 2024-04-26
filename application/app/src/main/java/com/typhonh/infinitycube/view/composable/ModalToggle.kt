package com.typhonh.infinitycube.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ModalToggle(icon: Painter, text: String, description: String, callback: () -> Unit = {}) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = callback,
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
        ) {
            Icon(painter = icon, modifier = Modifier.fillMaxSize(), tint= MaterialTheme.colorScheme.primary, contentDescription = description)
        }
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}