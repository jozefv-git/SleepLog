package com.stopstudiovm.sleeplog.feature_sleep.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep

@Database(
    entities = [Sleep::class],
    version = 1
)

abstract class SleepDatabase: RoomDatabase(){
    abstract val sleepDao: SleepDao

    companion object {
        const val DATABASE_NAME = "sleep_db"
    }
}