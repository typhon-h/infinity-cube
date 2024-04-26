package com.typhonh.infinitycube.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EffectSettingsSheet(state: Boolean, onDismissRequest: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if(state) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.fillMaxSize()){

            }
        }
    }
}