package com.stopstudiovm.sleeplog.feature_sleep.domain.use_case

import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.domain.repository.SleepRepository
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.OrderType
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SleepOrder
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

// All sleeps from the DB + Business logic - ordering
class GetSleeps(private val repository: SleepRepository) {
    // Default ordering by date descending
    operator fun invoke(sleepOrder: SleepOrder = SleepOrder.Date(OrderType.Descending)):
            Flow<List<Sleep>>{ // This will return our flow with list of Sleeps
        return repository.getSleeps().map { sleeps ->
            when(sleepOrder.orderType){
                is OrderType.Ascending ->{
                    when(sleepOrder){
                        is SleepOrder.Date -> sleeps.sortedBy { it.date }
                        is SleepOrder.Color -> sleeps.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (sleepOrder) {
                        is SleepOrder.Date -> sleeps.sortedByDescending { it.date }
                        is SleepOrder.Color -> sleeps.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}