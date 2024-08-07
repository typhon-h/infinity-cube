package com.typhonh.infinitycube.view.composable.sheet

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.typhonh.infinitycube.R
import com.typhonh.infinitycube.controller.InfinityCubeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesSettingsSheet(state: Boolean, viewModel: InfinityCubeViewModel, onDismissRequest: () -> Unit = {}) {
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showConfirmReset by remember { mutableStateOf(false) }
    var showConfirmClearPreset by remember { mutableStateOf(false) }

    if (showConfirmReset) {
        AlertDialog(
            onDismissRequest = { showConfirmReset = false},
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmReset = false
                        viewModel.factoryReset(context)
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error, contentColor = MaterialTheme.colorScheme.onError)
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmReset = false},
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.onSecondary)
                ) {
                    Text("Cancel")
                }
            },
            title = { Text("Confirm Factory Reset") }
        )
    }

    if (showConfirmClearPreset) {
        AlertDialog(
            onDismissRequest = { showConfirmClearPreset = false},
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmClearPreset = false
                        viewModel.resetDatabase()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error, contentColor = MaterialTheme.colorScheme.onError)
                ) {
                    Text("Clear All")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmClearPreset = false},
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.onSecondary)
                ) {
                    Text("Cancel")
                }
            },
            title = { Text("Confirm Factory Reset") }
        )
    }

    if (state) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismissRequest()
            },
            sheetState = sheetState
        ) {
            var address by remember { mutableStateOf(viewModel.getAddress()) }
            var ssid by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordMask by remember { mutableStateOf<VisualTransformation>(PasswordVisualTransformation()) }
            var passwordIcon by remember { mutableIntStateOf( R.drawable.visibility_off ) }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Settings", style = MaterialTheme.typography.displaySmall)
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("SSID:")
                        TextField(
                            value = ssid,
                            onValueChange = { ssid = it},
                            placeholder = { Text("MyNetwork") },
                            singleLine = true
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Password:")
                        TextField(
                            value = password,
                            onValueChange = { password = it},
                            placeholder = { Text("MyPassword") },
                            singleLine = true,
                            visualTransformation = passwordMask,
                            trailingIcon = {Icon(painter = painterResource(passwordIcon), "Password Visibility Toggle", modifier = Modifier.clickable {
                                if (passwordMask is PasswordVisualTransformation) {
                                    passwordMask = VisualTransformation.None
                                    passwordIcon = R.drawable.visibility
                                } else {
                                    passwordMask = PasswordVisualTransformation()
                                    passwordIcon = R.drawable.visibility_off
                                }
                            })}
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.setWifi(ssid.trim(), password.trim(), context)
                            ssid = ""
                            password = ""
                        },
                        modifier = Modifier.padding(10.dp),
                        enabled = ssid.isNotBlank() && password.isNotBlank()
                    ) {
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
                            if (viewModel.getAddress() != address) {
                                viewModel.setAddress(context, address)
                                Toast.makeText(context, "Address Updated!", Toast.LENGTH_SHORT).show()
                            }

                        },
                        modifier = Modifier.padding(10.dp),
                        enabled = viewModel.getAddress() != address
                    ) {
                        Text("Update Address")
                    }
                }

                Button(onClick = { showConfirmClearPreset = true }) {
                    Text("Clear Presets")
                }

                Button(onClick = { showConfirmReset = true }) {
                    Text("Factory Reset Device")
                }
            }
        }
    }
}