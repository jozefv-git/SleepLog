package com.stopstudiovm.sleeplog.feature_sleep.domain.use_case

import com.stopstudiovm.sleeplog.feature_sleep.domain.model.InvalidSleepException
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.domain.repository.SleepRepository

// Add sleep
class AddSleep(private val repository: SleepRepository) {
    @Throws(InvalidSleepException::class)
    // With operator fun invoke we can call this class as a fun
    suspend operator fun invoke(sleep: Sleep){
        if(sleep.quality.isBlank()){
            throw InvalidSleepException("The quality of the sleep can't be empty.")
        }
        if(sleep.date.isBlank()){
            throw InvalidSleepException("Insert the date.")
        }
        repository.insertSleep(sleep)
    }
}