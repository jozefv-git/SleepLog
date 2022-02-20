package com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep

import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.OrderType
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SleepOrder

// This will contain all states what user can do in the UI
data class SleepsState(
    val sleeps: List<Sleep> = emptyList(),
    val sleepOrder: SleepOrder = SleepOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
