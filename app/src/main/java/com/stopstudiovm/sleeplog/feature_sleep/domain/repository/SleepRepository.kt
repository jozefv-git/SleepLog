package com.stopstudiovm.sleeplog.feature_sleep.domain.repository

import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import kotlinx.coroutines.flow.Flow

// These are our definitions (so same functions like in the dao) - we will implement it
// in the data repository where we will actual say - "Hey we are working with DB"
interface SleepRepository {
    fun getSleeps(): Flow<List<Sleep>>

    suspend fun getSleepById(id: Int): Sleep?
    suspend fun insertSleep(sleep: Sleep)
    suspend fun deleteSleep(sleep: Sleep)
}