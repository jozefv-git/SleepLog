package com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.stopstudiovm.sleeplog.feature_sleep.common.Constants
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.InvalidSleepException
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.domain.use_case.SleepUseCases
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditSleepViewModel @Inject constructor(
    private val sleepUseCases: SleepUseCases,
    // We can inject - Bundle which contains navigation arguments - this is from hilt
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // When we have a edit text field, it is not so optimal to wrap it into the one state, so it is better to have it separately...
    // Bcs every time when we click a letter it will reload the UI
    // We will need to create class which will hold states for our hint

    // Content state
    private val _sleepContent = mutableStateOf(
        SleepTextFieldState(
            hint = "Describe your sleep..."
        )
    )
    val sleepContent: State<SleepTextFieldState> = _sleepContent

    // Our states - we will get middle color .toArgb() will convert it to int
    private val _sleepQualityColor = mutableStateOf(Sleep.sleepQualityM["AVERAGE"]!!.toArgb())
    val sleepQualityColor: State<Int> = _sleepQualityColor

    private val _sleepQuality = mutableStateOf("AVERAGE")
    val sleepQuality: State<String> = _sleepQuality

    private var _sleepDate = mutableStateOf("")
    val sleepDate: State<String> = _sleepDate

    private val _sleepTimeHours = mutableStateOf(3)
    val sleepTimeHours: State<Int> = _sleepTimeHours

    private val _sleepTimeMins = mutableStateOf(0)
    val sleepTimeMins: State<Int> = _sleepTimeMins

    // We need to use SharedFlow if we want to represent one time event for example snack bar...
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow() // Public version of sharedFlow

    // Sleep item Id
    private var currentSleepId: Int? = null

    init {
        // Navigation argument will be of type int and it will be called sleepId
        savedStateHandle.get<Int>("sleepId")?.let { sleepId ->
            // -1 is our default value so if it is not default value, we will get values from our db and putting it into our state
            if(sleepId != -1) {
                viewModelScope.launch {
                    sleepUseCases.getSleep(sleepId)?.also { sleep ->
                        currentSleepId = sleep.id
                        if (sleep.content.isNotEmpty()){
                            _sleepContent.value = _sleepContent.value.copy(
                                text = sleep.content,
                                isHintVisible = false
                            )
                        }
                        _sleepQualityColor.value = sleep.color
                        _sleepTimeHours.value = sleep.sleepDurationHours
                        _sleepTimeMins.value = sleep.sleepDurationMinutes
                        //Test: Log.d("TimeH",sleep.sleepDurationHours.toString())
                        //Test: Log.d("TimeM",sleep.sleepDurationMinutes.toString())
                        _sleepDate.value = sleep.date
                        _sleepQuality.value = sleep.quality
                    }
                }
            }
        }
    }

    // Function which will take events from the edit screen
    fun onEvent(event: AddEditSleepEvent) {
        when(event) {
            is AddEditSleepEvent.EnteredContent -> {
                // We want to update sleepContent value with new value
                _sleepContent.value = _sleepContent.value.copy(
                    text = event.value
                )
            }
            // When we focus on our textField, we want to hide our hint
            is AddEditSleepEvent.ChangeContentFocus -> {
                _sleepContent.value = _sleepContent.value.copy(
                    // When we are not focused on the text field, we want to show the hint
                    // Also we want to be sure that hint will be shown only when text is empty
                    isHintVisible = !event.focusState.isFocused &&
                            _sleepContent.value.text.isBlank()
                )
            }
            is AddEditSleepEvent.ChangeColor -> {
                _sleepQualityColor.value = event.color
            }
            is AddEditSleepEvent.ChangeQuality -> {
                _sleepQuality.value = event.quality
            }
            is AddEditSleepEvent.ShowDatePicker -> {
                viewModelScope.launch {

                    // Date picker
                    val startYear = yearToMillis("UTC", Constants.PICKER_MIN_START_YEAR,
                        Constants.PICKER_MIN_START_MONTH,
                        Constants.PICKER_MIN_START_DAY)

                    val calConstraints = setPickerConstraints(startYear)

                    val picker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Date of sleep")
                        .setCalendarConstraints(calConstraints.build()).build()
                    event.activity.let {
                        picker.show(it.supportFragmentManager, picker.toString())
                        picker.addOnPositiveButtonClickListener {
                                date ->
                            Log.d("date",date.toString())
                            _sleepDate.value = unixToDate(date)
                        }
                    }

                }
            }
            is AddEditSleepEvent.ShowTimePicker -> {
                _sleepTimeHours.value = event.time.hours
                _sleepTimeMins.value = event.time.minutes
            }
            is AddEditSleepEvent.SaveSleep -> {
                viewModelScope.launch {
                    try {
                        sleepUseCases.addSleep(
                            Sleep(
                                content = sleepContent.value.text,
                                date = sleepDate.value,
                                color = sleepQualityColor.value,
                                id = currentSleepId,
                                sleepDurationHours = sleepTimeHours.value,
                                sleepDurationMinutes = sleepTimeMins.value,
                                quality = sleepQuality.value,
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveSleep) // So we can react on that on the screen
                    } catch(e: InvalidSleepException) { // If the title content was empty
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save sleep"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveSleep: UiEvent() // When we click on the button, we will save the note and after we save it, we want to navigate back
    }


    // Constrains for datePicker
    private fun setPickerConstraints(startYear: Long): CalendarConstraints.Builder{
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        return CalendarConstraints.Builder()
            .setStart(startYear)
            .setEnd(today)
            .setValidator(DateValidatorPointBackward.now())
    }
}