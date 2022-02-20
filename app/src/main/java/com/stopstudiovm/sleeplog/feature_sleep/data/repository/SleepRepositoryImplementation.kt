package com.stopstudiovm.sleeplog.feature_sleep.data.repository

import com.stopstudiovm.sleeplog.feature_sleep.data.data_source.SleepDao
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.domain.repository.SleepRepository
import kotlinx.coroutines.flow.Flow

// Implementation repository calling functions from our Dao Object
class SleepRepositoryImplementation(private val dao: SleepDao) : SleepRepository {
    override fun getSleeps(): Flow<List<Sleep>> {
        return dao.getSleeps()
    }

    override suspend fun getSleepById(id: Int): Sleep? {
        return dao.getSleepById(id)
    }

    override suspend fun insertSleep(sleep: Sleep) {
        return dao.insertSleep(sleep)
    }

    override suspend fun deleteSleep(sleep: Sleep) {
        return dao.deleteSleep(sleep)
    }

}