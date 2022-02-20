package com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep

import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SleepOrder

// Variables relevant for our sleep list UI
sealed class SleepsEvent{
    data class Order(val sleepOrder: SleepOrder): SleepsEvent()
    data class DeleteSleep(val sleep: Sleep): SleepsEvent()
    object RestoreSleep: SleepsEvent()
    object ToggleOrderSection: SleepsEvent()
}
