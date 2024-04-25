package com.typhonh.infinitycube.view

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
}
