package com.stopstudiovm.sleeplog.feature_sleep.domain.use_case

// This will contains all useCases and this will be injected into our viewModel
data class SleepUseCases(
    val addSleep: AddSleep,
    val deleteSleep: DeleteSleep,
    val getSleep: GetSleep,
    val getSleeps: GetSleeps,
    val getDatePicker: GetDatePicker
)