package com.typhonh.infinitycube.view.composable.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.typhonh.infinitycube.controller.InfinityCubeViewModel
import com.typhonh.infinitycube.view.composable.DirectionSwitch
import com.typhonh.infinitycube.view.composable.DotWidthSlider
import com.typhonh.infinitycube.view.composable.EffectDropdown
import com.typhonh.infinitycube.view.composable.SpeedSlider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EffectSettingsSheet(state: Boolean, viewModel:InfinityCubeViewModel, onDismissRequest: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val effectState by viewModel.effectState.collectAsState()

    if(state) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(10.dp, 20.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally){

                Text("Effect Settings", style=MaterialTheme.typography.displaySmall)

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Direction")
                    DirectionSwitch()
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                   Text("Effect")
                    EffectDropdown()
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Symmetry")
                    EffectDropdown()
                }

                HorizontalDivider()

                if(true) { // TODO: If effect is chase
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text("Dot Width")
                        DotWidthSlider()
                    }
                    HorizontalDivider()
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text("Dot Spacing")
                        DotWidthSlider()
                    }
                } else {
                    Box(modifier = Modifier.fillMaxHeight(0.58f)) {}
                }
                HorizontalDivider()

                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Speed")
                    SpeedSlider()
                }

            }
        }
    }
}