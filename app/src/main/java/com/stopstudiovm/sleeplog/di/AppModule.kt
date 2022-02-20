package com.stopstudiovm.sleeplog.di

import android.app.Application
import androidx.room.Room
import com.stopstudiovm.sleeplog.feature_sleep.data.data_source.SleepDatabase
import com.stopstudiovm.sleeplog.feature_sleep.data.repository.SleepRepositoryImplementation
import com.stopstudiovm.sleeplog.feature_sleep.domain.repository.SleepRepository
import com.stopstudiovm.sleeplog.feature_sleep.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSleepDatabase(app: Application): SleepDatabase {
        return Room.databaseBuilder(
            app,
            SleepDatabase::class.java,
            SleepDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideSleepRepository(db: SleepDatabase): SleepRepository{
        return SleepRepositoryImplementation(db.sleepDao)
    }

    @Provides
    @Singleton
    fun provideSleepUseCases(repository: SleepRepository): SleepUseCases{
        return SleepUseCases(
            getSleep = GetSleep(repository),
            getSleeps = GetSleeps(repository),
            addSleep = AddSleep(repository),
            deleteSleep = DeleteSleep(repository),
            getDatePicker = GetDatePicker()
        )
    }
}