package com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.focus.FocusState
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.Hours

// We need to have event for basically each action which user can create

sealed class AddEditSleepEvent{
    data class EnteredContent(val value: String): AddEditSleepEvent()
    // Focus state for hiding our hints
    data class ChangeContentFocus(val focusState: FocusState): AddEditSleepEvent()
    data class ChangeColor(val color: Int): AddEditSleepEvent()
    data class ChangeQuality(val quality: String): AddEditSleepEvent()
    data class ShowDatePicker(val activity: AppCompatActivity): AddEditSleepEvent()
    data class ShowTimePicker(val time: Hours): AddEditSleepEvent()
    // Click on our float action button
    object SaveSleep: AddEditSleepEvent()

}