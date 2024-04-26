package com.typhonh.infinitycube.view.composable.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesSettingsSheet(state: Boolean, onDismissRequest: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (state) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Settings", style = MaterialTheme.typography.displaySmall)
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("SSID:")
                        TextField("MyNetwork", {})
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Password:")
                        TextField("MyPassword", {})
                    }
                    Button(onClick = {}, modifier = Modifier.padding(10.dp)) {
                        Text("Update Wifi")
                    }
                }

                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Address:")
                        TextField("MyMDNS", {})
                    }
                    Button(onClick = {}, modifier = Modifier.padding(10.dp)) {
                        Text("Update Address")
                    }
                }

                Button(onClick = {}) {
                    Text("Clear Presets")
                }

                Button(onClick = {}) {
                    Text("Factory Reset Device")
                }

            }
        }
    }
}