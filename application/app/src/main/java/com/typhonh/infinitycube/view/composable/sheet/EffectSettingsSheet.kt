package com.typhonh.infinitycube.view.composable.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.typhonh.infinitycube.R
import com.typhonh.infinitycube.controller.InfinityCubeViewModel
import com.typhonh.infinitycube.model.entity.DirectionType
import com.typhonh.infinitycube.model.entity.EffectType
import com.typhonh.infinitycube.model.entity.SymmetryType
import com.typhonh.infinitycube.view.composable.EffectDropdown
import com.typhonh.infinitycube.view.composable.SettingsSlider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EffectSettingsSheet(state: Boolean, viewModel: InfinityCubeViewModel, onDismissRequest: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val effectState by viewModel.effectState.collectAsState()

    if(state) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 5.dp), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally)
            {

                Text("Effect Settings", style = MaterialTheme.typography.displaySmall)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Direction")
                    EffectDropdown(DirectionType.values(), effectState.direction) {
                        viewModel.setDirection(it)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Symmetry")
                    EffectDropdown(SymmetryType.values(), effectState.symmetry) {
                        viewModel.setSymmetry(it)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Effect")
                    EffectDropdown(EffectType.values(), effectState.name) {
                        viewModel.setEffect(it)
                    }
                }

                if (effectState.name == EffectType.CHASE) {
                    HorizontalDivider()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Dot Width")
                        SettingsSlider(
                            defaultValue = effectState.dotWidth,
                            valueRange = 1f..5f,
                            steps = 5,
                            showValue = true
                        ) {
                            viewModel.setDotWidth(it)
                        }
                    }
                    HorizontalDivider()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Dot Spacing")
                        SettingsSlider(
                            defaultValue = effectState.dotSpacing,
                            valueRange = 1f..10f,
                            steps = 14,
                            showValue = true
                        ) {
                            viewModel.setDotSpacing(it)
                        }
                    }
                    HorizontalDivider()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Dot Blur")
                        SettingsSlider(
                            defaultValue = effectState.dotBlur,
                            valueRange = 0f..255f,
                            showValue = true
                        ) {
                            viewModel.setDotBlur(it)
                        }
                    }
                } else if (effectState.name == EffectType.MOTION) {
                    HorizontalDivider()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Motion Range")
                        SettingsSlider(
                            defaultValue = effectState.motionRange,
                            valueRange = 0f..255f,
                            showValue = true
                        ) {
                            viewModel.setMotionRange(it)
                        }
                    }
                }

                HorizontalDivider()

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Speed")
                    SettingsSlider(
                        defaultValue = effectState.speed,
                        valueRange = 0f..255f,
                        showPercentage = true,
                        minButton = { modifier ->
                            IconButton(
                                modifier = modifier,
                                onClick = { viewModel.setSpeed(0f) }
                            ) {
                                Icon(painter = painterResource(R.drawable.bolt_hollow),
                                    contentDescription = "Speed Down",
                                )
                            }
                        },
                        maxButton = { modifier ->
                            IconButton(
                                modifier = modifier,
                                onClick = { viewModel.setSpeed(255f) }
                            ) {
                                Icon(painter = painterResource(R.drawable.bolt_fill),
                                    contentDescription = "Speed Up",
                                )
                            }
                        }
                    ) {
                        viewModel.setSpeed(it)
                    }
                }
            }
        }
    }
}