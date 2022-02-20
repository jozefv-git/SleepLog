package com.stopstudiovm.sleeplog.feature_sleep.presentation.util

sealed class SleepOrder(val orderType: OrderType){
    class Date(orderType: OrderType): SleepOrder(orderType)
    class Color(orderType: OrderType): SleepOrder(orderType)

    fun copy(orderType: OrderType): SleepOrder {
        return when(this) {
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
