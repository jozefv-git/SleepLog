package com.stopstudiovm.sleeplog.feature_sleep.presentation.util
import kotlinx.datetime.Instant
import java.util.*

class TimeConversion {

}

// Transform unixTime to date
fun unixToDate(timeMs : Long) : String{
    val stringTime = Instant.fromEpochMilliseconds(timeMs).toString()
    val onlyDate = stringTime.subSequence(0, stringTime.indexOf('T'))
    return onlyDate.toString()
}

// Conversion of selected date into the millis
fun yearToMillis(timezone: String,year: Int, month: Int, day: Int): Long{
    val calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))
    calendar.set(year, month, day)
    return calendar.timeInMillis
}