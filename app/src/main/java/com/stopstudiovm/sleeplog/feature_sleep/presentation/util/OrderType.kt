package com.stopstudiovm.sleeplog.feature_sleep.presentation.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()

}
