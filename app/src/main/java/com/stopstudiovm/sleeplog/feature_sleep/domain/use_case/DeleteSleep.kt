package com.stopstudiovm.sleeplog.feature_sleep.domain.use_case

import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.domain.repository.SleepRepository

// Delete sleep
class DeleteSleep(private val repository: SleepRepository) {
    suspend operator fun invoke(sleep: Sleep){
        repository.deleteSleep(sleep)
    }
}