package com.typhonh.infinitycube.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import io.mhssn.colorpicker.ColorPickerDialog
import io.mhssn.colorpicker.ColorPickerType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ColorSettingsSheet(state: Boolean, onDismissRequest: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showColorPicker by remember { mutableStateOf(false)}

    if(state) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
                Text("Color Palette", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(0.dp,5.dp))
                Box(modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue) //TODO: Make this get the list of set colors
                        )
                    )
                    .fillMaxWidth()
                    .weight(0.4f)
                    .aspectRatio(1f))

                Column(modifier = Modifier.fillMaxWidth().weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                    listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue).forEachIndexed { i, item ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().height(80.dp).padding(0.dp, 10.dp)) {
                            Icon(Icons.Default.Menu, contentDescription = "Remove Color", modifier = Modifier.weight(0.15f))
                            Box(modifier = Modifier.weight(0.7f).fillMaxHeight().background(item).clickable{showColorPicker = true})
                            IconButton(
                                onClick = {},
                                modifier = Modifier.weight(0.15f).fillMaxHeight(),
                                enabled = true // len > 1
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Remove Color")
                            }
                        }
                    }

                    IconButton(onClick = {showColorPicker = true}, modifier = Modifier.border(Dp.Hairline, MaterialTheme.colorScheme.onBackground, CircleShape)) {
                        Icon(Icons.Default.Add, "Add Color")
                    }
                }

            }
        }

        ColorPickerDialog(
            show = showColorPicker,
            type = ColorPickerType.Circle(showAlphaBar = false),
            properties = DialogProperties(),
            onDismissRequest = {
                showColorPicker = false
            },
            onPickedColor = {
                showColorPicker = false
            },
        )
    }
}