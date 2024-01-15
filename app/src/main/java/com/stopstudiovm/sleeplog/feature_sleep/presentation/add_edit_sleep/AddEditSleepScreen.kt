package com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep.components.RoundedButton
import com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep.components.TransparentHintTextField
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.FullHours
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.Hours
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.HoursNumberPicker
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SpacerVerM
import com.stopstudiovm.sleeplog.ui.theme.*
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditSleepScreen(
    spacesShapes: SpacesShapes = SpacesShapes(),
    activity: AppCompatActivity,
    navController: NavController,
    addEditState: AddEditSleepState,
    eventFlow: SharedFlow<AddEditSleepViewModel.UiEvent>,
    sleepColor: Int,
    onEvent: (AddEditSleepEvent) -> Unit
) {
    // Time picker
    var timePicker: Hours = FullHours(addEditState.sleepTimeHours, addEditState.sleepTimeMinutes)

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    // When we switch the color it will change the background with animation
    val sleepBackgroundAnimatable = remember {
        Animatable(
            // We are checking if we clicked on existing sleep
            // sleepColor is color from our navigation
            Color((if (sleepColor != -1) sleepColor else addEditState.sleepQualityColor))
        )
    }
    // For animation
    val scope = rememberCoroutineScope()
    // Key true, so we will get snack only once, not everytime when we do recompose
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is AddEditSleepViewModel.UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditSleepViewModel.UiEvent.SaveSleep -> {
                    // So we just go back to our notes screen
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = spacesShapes.shapes.large,
                onClick = {
                    onEvent(AddEditSleepEvent.SaveSleep)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save sleep")
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }) {
        // Actual edit screen content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(sleepBackgroundAnimatable.value)
                .padding(spacesShapes.spaces.spaceXL)
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Select your quality of sleep"
            )
            SpacerVerM()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacesShapes.spaces.spaceM),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Rounded button creation
                Sleep.sleepQualityM.forEach { quality ->
                    val sleepQualityColorInt = quality.value.toArgb()
                    val sleepQualityTxt = quality.key

                    val buttonColor =
                        if (addEditState.sleepQualityColor == sleepQualityColorInt) {
                            Color.Black
                        } else Color.Transparent

                    RoundedButton(
                        text = quality.key,
                        background = quality.value,
                        borderColor = buttonColor,
                        onClick = {
                            scope.launch {
                                sleepBackgroundAnimatable.animateTo(
                                    targetValue = Color(sleepQualityColorInt),
                                    // Duration of the animation
                                    animationSpec = tween(
                                        durationMillis = 500
                                    )
                                )
                            }
                            onEvent(AddEditSleepEvent.ChangeColor(sleepQualityColorInt))
                            onEvent(AddEditSleepEvent.ChangeQuality(sleepQualityTxt))
                        }
                    )
                }
            }
            SpacerVerM()
            Divider()
            SpacerVerM()
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Date of sleep"
            )
            SpacerVerM()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacesShapes.spaces.spaceM, end = spacesShapes.spaces.spaceM),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = addEditState.sleepDate,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )

                RoundedButton(
                    text = "DATE",
                    background = Violet,
                    borderColor = Color.Transparent,
                    onClick = { onEvent(AddEditSleepEvent.ShowDatePicker(activity)) }
                )
            }
            SpacerVerM()
            Divider()
            SpacerVerM()
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Length of sleep"
            )
            SpacerVerM()
            HoursNumberPicker(
                Modifier.padding(spacesShapes.spaces.spaceM),
                dividersColor = DarkGray,
                value = timePicker,
                textStyle = MaterialTheme.typography.titleMedium,
                onValueChange = {
                    timePicker = it
                    onEvent(AddEditSleepEvent.ShowTimePicker(it))
                },
                hoursDivider = {
                    Text(
                        modifier = Modifier.size(24.dp),
                        textAlign = TextAlign.Center,
                        text = "h",
                        color = DarkGray
                    )
                },
                minutesDivider = {
                    Text(
                        modifier = Modifier.size(24.dp),
                        textAlign = TextAlign.Center,
                        text = "m",
                        color = DarkGray
                    )
                }
            )
            SpacerVerM()
            Divider()
            SpacerVerM()
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Sleep description"
            )
            SpacerVerM()
            TransparentHintTextField(
                text = addEditState.sleepTextField.text,
                hint = addEditState.sleepTextField.hint,
                // Whenever we type something - we will passing the new value
                onValueChange = {
                    onEvent(AddEditSleepEvent.EnteredContent(it))
                },
                onFocusChange = {
                    onEvent(AddEditSleepEvent.ChangeContentFocus(it))
                },
                isHintVisible = addEditState.sleepTextField.isHintVisible,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(spacesShapes.spaces.spaceM)
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddEditSleepScreenPreview() {
    //AddEditSleepScreen(LightBlue.toArgb())
}