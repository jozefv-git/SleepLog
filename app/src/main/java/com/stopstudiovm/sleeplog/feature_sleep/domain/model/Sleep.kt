package com.stopstudiovm.sleeplog.feature_sleep.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.stopstudiovm.sleeplog.ui.theme.*
import java.lang.Exception

// Table in our db with unique date - if date exist, row will be updated
@Entity(indices = [Index(value = ["date"], unique = true)])
data class Sleep(
    val date: String,
    val content: String,
    val sleepDurationHours: Int,
    val sleepDurationMinutes: Int,
    val quality: String,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    // Predefined values for sleep quality
    companion object {
        val sleepQualityM = mapOf("POOR" to MaterialBlue1, "FAIR" to MaterialBlue2,
            "AVERAGE" to MaterialBlue3, "GOOD" to MaterialBlue4, "BEST" to MaterialBlue5)
    }
}

// Exceptions
class InvalidSleepException(message: String): Exception(message)
