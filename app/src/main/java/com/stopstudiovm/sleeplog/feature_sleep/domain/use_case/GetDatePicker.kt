package com.stopstudiovm.sleeplog.feature_sleep.domain.use_case

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.stopstudiovm.sleeplog.feature_sleep.common.Constants
import java.util.*

// Get date picker
class GetDatePicker {

     operator fun invoke(activity: AppCompatActivity): Long{

         val startYear = yearToMillis("UTC",Constants.PICKER_MIN_START_YEAR,
             Constants.PICKER_MIN_START_MONTH,
             Constants.PICKER_MIN_START_DAY)

         val calConstraints = setPickerConstraints(startYear)
         var selection: Long = 0

         val picker = MaterialDatePicker.Builder.datePicker()
             .setTitleText("Date of sleep")
             .setCalendarConstraints(calConstraints.build()).build()
         activity.let {
             picker.show(it.supportFragmentManager, picker.toString())
             picker.addOnPositiveButtonClickListener {
                     date ->
                 Log.d("date",date.toString())
                 selection = date
             }
         }
         return selection
    }

    // Constrains for datePicker
    private fun setPickerConstraints(startYear: Long): CalendarConstraints.Builder{
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        return CalendarConstraints.Builder()
            .setStart(startYear)
            .setEnd(today)
            .setValidator(DateValidatorPointBackward.now())
    }

    // Conversion of selected date into the millis
    private fun yearToMillis(timezone: String,year: Int, month: Int, day: Int): Long{
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))
        calendar.set(year, month, day)
        return calendar.timeInMillis
    }

}