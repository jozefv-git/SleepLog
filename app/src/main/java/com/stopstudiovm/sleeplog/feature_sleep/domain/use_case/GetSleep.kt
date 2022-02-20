package com.stopstudiovm.sleeplog.feature_sleep.domain.use_case

import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.domain.repository.SleepRepository

// Get sleep based on the Id
class GetSleep(private val repository: SleepRepository) {
    suspend operator fun invoke(id: Int): Sleep?{
        return repository.getSleepById(id)
    }
}