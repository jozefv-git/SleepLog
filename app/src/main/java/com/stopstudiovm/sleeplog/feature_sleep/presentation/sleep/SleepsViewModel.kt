package com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.domain.use_case.SleepUseCases
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.OrderType
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SleepOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepsViewModel @Inject constructor(
    private val sleepUseCases: SleepUseCases
): ViewModel() {
    // Our states - it will contains the values what our UI can observe
    private val _state = mutableStateOf(SleepsState())
    val state: State<SleepsState> = _state

    // Reference to last deleted sleep
    private var recentlyDeletedSleep: Sleep? = null

    // Job for cancel our coroutine
    private var getSleepsJob: Job? = null

    // Notes by default order
    init {
        getSleeps(SleepOrder.Date(OrderType.Descending))
    }

    // We will  trigger this fun from UI
    fun onEvent(event: SleepsEvent){
        when(event){
            is SleepsEvent.Order -> {
                // Check if we are clicking on the same button
                // We want to compare classes not references
                if (state.value.sleepOrder::class == event.sleepOrder::class &&
                        state.value.sleepOrder.orderType == event.sleepOrder.orderType){
                    return
                }
                getSleeps(event.sleepOrder)
            }
            is SleepsEvent.DeleteSleep -> {
                // We will launch coroutines in viewModel scope
                viewModelScope.launch {
                    // We can call this class as a function bcs. we override our invoke operator
                    sleepUseCases.deleteSleep(event.sleep)
                    recentlyDeletedSleep = event.sleep
                }
            }
            // This will be called when we will click on undo
            is SleepsEvent.RestoreSleep -> {
                viewModelScope.launch {
                    sleepUseCases.addSleep(
                        // If it is null we will only return lunch
                        recentlyDeletedSleep ?: return@launch
                    )
                    // So if we will call undo multiple times, we will not insert our not over and over again
                    recentlyDeletedSleep = null
                }
            }
            is SleepsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    // So we will update this with same value( copy) but the value of the copy can be changed now
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
    // We need to cancel old coroutine
    private fun getSleeps(sleepOrder: SleepOrder) {
        getSleepsJob?.cancel()
        // This will return flow that comes from our db
        // it will trigger this flow when something new will change in our database
        getSleepsJob = sleepUseCases.getSleeps(sleepOrder)
            .onEach { sleeps ->
                _state.value = state.value.copy(
                    sleeps = sleeps,
                    sleepOrder = sleepOrder
                )
            }.launchIn(viewModelScope)
    }
}