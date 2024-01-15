package com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep

import androidx.compose.ui.graphics.toArgb
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep

data class AddEditSleepState(
    val sleepQualityColor: Int = Sleep.sleepQualityM["AVERAGE"]!!.toArgb(),
    val sleepQuality: String = "AVERAGE",
    val sleepDate: String = "",
    val sleepTimeHours: Int = 8,
    val sleepTimeMinutes: Int = 20,
    val sleepTextField: SleepTextFieldState = SleepTextFieldState()
)

// Holder for edit text view
data class SleepTextFieldState(
    val text: String = "",
    val hint: String = "Describe your sleep...",
    val isHintVisible: Boolean = true
)
