package com.stopstudiovm.sleeplog.feature_sleep.presentation.util

// This will contains our different screens and the corresponding routes
sealed class Screen(val route: String){
    object SleepsScreen: Screen("sleeps_screen")
    object AddEditSleepScreen: Screen("add_edit_sleep_screen")
}
