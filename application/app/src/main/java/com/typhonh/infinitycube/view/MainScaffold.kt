package com.typhonh.infinitycube.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.typhonh.infinitycube.controller.InfinityCubeViewModel
import com.typhonh.infinitycube.view.composable.sheet.PreferencesSettingsSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    viewModel: InfinityCubeViewModel,
    content: @Composable () -> Unit
) {
    var showSettingsSheet by remember { mutableStateOf(false) }

    Scaffold(
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize(), contentAlignment = Alignment.Center) {
                content()
            }
            PreferencesSettingsSheet(showSettingsSheet, onDismissRequest = {showSettingsSheet = false}, viewModel = viewModel)
        },
        topBar = {
            TopAppBar(title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Infinity Cube", style = MaterialTheme.typography.displaySmall)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(text = "Connected", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.outline)
                            Box(modifier = Modifier.background(Color.Green, CircleShape).size(10.dp))
                        }
                    }

                    IconButton(modifier = Modifier.align(Alignment.TopEnd),
                        onClick = {
                            showSettingsSheet = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            })
        }
    )
}