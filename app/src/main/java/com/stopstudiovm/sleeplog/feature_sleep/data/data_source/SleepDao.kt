package com.stopstudiovm.sleeplog.feature_sleep.data.data_source

import androidx.room.*
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import kotlinx.coroutines.flow.Flow

// Functions what we want to do with our db
@Dao
interface SleepDao {
    // Return flow so don't need to be suspend
    @Query("SELECT * FROM sleep")
    fun getSleeps(): Flow<List<Sleep>>

    // Select all with id what we passed colon
    @Query("SELECT * FROM sleep WHERE id = :id")
    suspend fun getSleepById(id: Int): Sleep?

    // We want to insert note and if the ID already exist, then we will update it, we don't need to have fun for update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSleep(sleep: Sleep)

    @Delete
    suspend fun deleteSleep(sleep: Sleep)
}