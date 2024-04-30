package com.typhonh.infinitycube.view.composable.sheet

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.typhonh.infinitycube.controller.InfinityCubeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesSettingsSheet(state: Boolean, onDismissRequest: () -> Unit = {}, viewModel: InfinityCubeViewModel) {
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (state) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismissRequest()
            },
            sheetState = sheetState
        ) {
            var address by remember { mutableStateOf(viewModel.mdnsAddress)}

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
                        TextField(address, onValueChange = {
                            address = it
                        })
                    }
                    Button(
                        onClick = {
                            if (viewModel.mdnsAddress != address) {
                                viewModel.updateAddress(context, address)
                                Toast.makeText(context, "Address Updated!", Toast.LENGTH_SHORT).show()
                            }

                        },
                        modifier = Modifier.padding(10.dp),
                        enabled = viewModel.mdnsAddress != address
                    ) {
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