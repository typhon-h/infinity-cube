package com.typhonh.infinitycube.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val description: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String
)

@Composable
fun getNavigationItems(): List<NavigationItem> {
    return listOf(
    )
}
