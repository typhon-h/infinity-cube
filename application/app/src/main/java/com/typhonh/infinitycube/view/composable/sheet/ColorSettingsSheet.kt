package com.typhonh.infinitycube.view.composable.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.typhonh.infinitycube.controller.InfinityCubeViewModel
import io.mhssn.colorpicker.ColorPickerDialog
import io.mhssn.colorpicker.ColorPickerType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ColorSettingsSheet(state: Boolean, viewModel: InfinityCubeViewModel, onDismissRequest: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showColorPicker by remember { mutableStateOf(false) }
    val effectState by viewModel.effectState.collectAsState()
    var selectedColor by remember { mutableIntStateOf(-1) }

    if(state) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
                Text("Color Palette", style = MaterialTheme.typography.titleLarge, modifier = Modifier
                    .padding(0.dp, 5.dp)
                    .weight(1f))
                Box(modifier = Modifier
                    .background(
                        Brush.linearGradient(
                            effectState.color.map { Color(it.r, it.g, it.b) }
                        )
                    )
                    .fillMaxWidth()
                    .weight(4f)
                    .aspectRatio(1f))

                Spacer(modifier = Modifier.weight(0.5f))

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f), horizontalAlignment = Alignment.CenterHorizontally) {
                    effectState.color.map { Color(it.r, it.g, it.b) }.forEachIndexed { i, item ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .border(3.dp, MaterialTheme.colorScheme.background)) {
                            Box(modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(item)
                                .clickable {
                                    showColorPicker = true
                                    selectedColor = i
                                })
                        }
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
                selectedColor = -1
            },
            onPickedColor = {
                showColorPicker = false
                if (selectedColor != -1) {
                    viewModel.setColor(selectedColor, it)
                }
            },
        )
    }
}