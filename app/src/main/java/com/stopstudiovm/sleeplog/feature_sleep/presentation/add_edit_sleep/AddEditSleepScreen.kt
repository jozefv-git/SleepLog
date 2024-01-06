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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep.components.RoundedButton
import com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep.components.TransparentHintTextField
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.FullHours
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.Hours
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.HoursNumberPicker
import com.stopstudiovm.sleeplog.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditSleepScreen(
    navController: NavController,
    sleepColor: Int,
    viewModel: AddEditSleepViewModel = hiltViewModel(),
    activity: AppCompatActivity
) {
    // References from our ViewModel
    val contentState = viewModel.sleepContent.value
    val dateState = viewModel.sleepDate.value

    val hoursState = viewModel.sleepTimeHours.value
    val minutesState = viewModel.sleepTimeMins.value
    // Time picker
    var timePicker: Hours = FullHours(hoursState, minutesState)

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    // When we switch the color it will change the background with animation
    val sleepBackgroundAnimatable = remember {
        Animatable(
            // We are checking if we clicked on existing sleep
            // sleepColor is color from our navigation
            Color((if (sleepColor != -1) sleepColor else viewModel.sleepQualityColor.value))
        )
    }
    // For animation
    val scope = rememberCoroutineScope()
    // Key true, so we will get snack only once, not everytime when we do recompose
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditSleepViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
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
                onClick = {
                    viewModel.onEvent(AddEditSleepEvent.SaveSleep)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save sleep")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }) {
        // Actual edit screen content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(sleepBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Select your quality of sleep"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Rounded button creation
                Sleep.sleepQualityM.forEach { quality ->
                    val sleepQualityColorInt = quality.value.toArgb()
                    val sleepQualityTxt = quality.key

                    val buttonColor =
                        if (viewModel.sleepQualityColor.value == sleepQualityColorInt) {
                            Color.Black
                        } else Color.Transparent

                    RoundedButton(text = quality.key,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        textColor = MaterialTheme.colorScheme.onSurface,
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
                            viewModel.onEvent(AddEditSleepEvent.ChangeColor(sleepQualityColorInt))
                            viewModel.onEvent(AddEditSleepEvent.ChangeQuality(sleepQualityTxt))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Date of sleep"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dateState,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )

                RoundedButton(text = "DATE",
                    textStyle = MaterialTheme.typography.bodyLarge,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    background = Violet,
                    borderColor = Color.Transparent,
                    onClick = { viewModel.onEvent(AddEditSleepEvent.ShowDatePicker(activity)) }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Length of sleep"
            )
            Spacer(modifier = Modifier.height(8.dp))
            HoursNumberPicker(
                Modifier.padding(8.dp),
                dividersColor = DarkGray,
                value = timePicker,
                textStyle = MaterialTheme.typography.titleMedium,
                onValueChange = {
                    timePicker = it
                    viewModel.onEvent(AddEditSleepEvent.ShowTimePicker(it))
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
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Sleep description"
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                // Whenever we type something - we will passing the new value
                onValueChange = {
                    viewModel.onEvent(AddEditSleepEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditSleepEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddEditSleepScreenPreview() {
    //AddEditSleepScreen(LightBlue.toArgb())
}