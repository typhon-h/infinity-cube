package com.typhonh.infinitycube.view.composable.sheet

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.typhonh.infinitycube.controller.InfinityCubeViewModel
import com.typhonh.infinitycube.model.entity.CubeState
import com.typhonh.infinitycube.model.entity.EffectState
import com.typhonh.infinitycube.model.entity.Preset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresetSettingsSheet(state: Boolean, viewModel: InfinityCubeViewModel, onDismissRequest: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val presets = viewModel.getPresets().collectAsState(initial = listOf())
    var showPresetSave by remember { mutableStateOf(false) }
    var showConfirmDelete by remember { mutableStateOf(false) }

    var savedPresetName by remember { mutableStateOf("") }
    val tempPreset = Preset(presetId = -1, presetName = "", effect = EffectState.defaultEffect, state = CubeState(false, 0f))
    var presetToDelete by remember { mutableStateOf(tempPreset) }

    if (showPresetSave) {
        AlertDialog(
            onDismissRequest = {
                showPresetSave = false
                savedPresetName = ""
            },
            confirmButton = {
                Button(
                    onClick = {
                        showPresetSave = false
                        viewModel.addPreset(
                            Preset(
                                presetName = savedPresetName,
                                state = viewModel.cubeState.value,
                                effect = viewModel.effectState.value
                            )
                        )
                    },
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showPresetSave = false
                        savedPresetName = ""
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.onSecondary)
                ) {
                    Text("Cancel")
                }
            },
            title = { Text("Save Current State as Preset") },
            text = {
                TextField(
                    value = savedPresetName,
                    onValueChange = { savedPresetName = it },
                    singleLine = true,
                    placeholder = { Text("My New Preset") }
                )
            }
        )
    }

    if (showConfirmDelete) {
        AlertDialog(
            onDismissRequest = {
                showConfirmDelete = false
                presetToDelete = tempPreset
                               },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmDelete = false
                        viewModel.deletePreset(presetToDelete)
                        presetToDelete = tempPreset
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error, contentColor = MaterialTheme.colorScheme.onError)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showConfirmDelete = false
                        presetToDelete = tempPreset
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.onSecondary)
                ) {
                    Text("Cancel")
                }
            },
            title = { Text("Delete Preset?") }
        )
    }

    if(state) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    savedPresetName = ""
                    showPresetSave = true
                }) {
                    Text("Save Current Settings as Preset")
                }
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),) {
                    items(presets.value) {
                        Row(modifier = Modifier
                            .border(Dp.Hairline, MaterialTheme.colorScheme.outline)
                            .fillMaxWidth()
                            .padding(15.dp)
                            .clickable { viewModel.setPreset(it) },
                            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(it.presetName, style = MaterialTheme.typography.headlineLarge)
                            IconButton(onClick = {
                                presetToDelete = it
                                showConfirmDelete = true
                            }) {
                                Icon(Icons.Default.Delete, "Delete Preset", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}