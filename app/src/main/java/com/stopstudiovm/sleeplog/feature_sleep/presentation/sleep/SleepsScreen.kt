package com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep.components.OrderSection
import com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep.components.SleepItem
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.Screen
import com.stopstudiovm.sleeplog.ui.theme.DarkGray
import com.stopstudiovm.sleeplog.ui.theme.RedOrange
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun SleepsScreen(
    navController: NavController,
    viewModel: SleepsViewModel = hiltViewModel()
) {

    // We need this for our snack bar
    val state = viewModel.state.value
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    // This for show of the SnackBar
    val scope = rememberCoroutineScope()

    val fabOffsetHeightPx = remember { mutableStateOf(0f) }
    val fabHeight = 72.dp // 56dp is FAB size but 16dp is padding so 72 for hide of the button
    val fabHeightPx = with(
        LocalDensity.current
    ) {
        fabHeight.roundToPx().toFloat()
    }

    // Hide FAB when scroll
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y
                val newOffset = fabOffsetHeightPx.value + delta
                fabOffsetHeightPx.value = newOffset.coerceIn(-fabHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection),
        snackbarHost = {
            // Reuse default SnackBarHost to have default animation and timing handling
            SnackbarHost(snackbarHostState) { data ->
                // Custom SnackBar with the custom colors
                Snackbar(
                    containerColor = RedOrange,
                    contentColor = DarkGray,
                    snackbarData = data
                )
            }
        },
        floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier
                        .offset {
                            IntOffset(x = 0, y = -fabOffsetHeightPx.value.roundToInt())
                        },
                    onClick = {
                        navController.navigate(Screen.AddEditSleepScreen.route)
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add sleep")
                }
        },
    ) {
        // Content of our scaffold (Top which contain selection buttons)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sleep list",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Normal
                )
                IconButton(
                    onClick = {
                        // We are sending action from our UI to our ViewModel
                        viewModel.onEvent(SleepsEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                // When this one will toggle, the animation will fire again
                visible = state.isOrderSectionVisible,
                // When it slides in
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                // What we want to animate
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    sleepOrder = state.sleepOrder,
                    // When we click on the button we want to send event to our view model
                    onOrderChange = {
                        viewModel.onEvent(SleepsEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Our list
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                // Getting all sleeps
                items(state.sleeps) { sleep ->
                    SleepItem(
                        sleep = sleep,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditSleepScreen.route +
                                            "?sleepId=${sleep.id}&sleepColor=${sleep.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(SleepsEvent.DeleteSleep(sleep))
                            // Showing snackBar need coroutine because it take some time for show up
                            scope.launch {
                                // Our result of our SnackBar
                                val result = snackbarHostState.showSnackbar(
                                    message = "Sleep deleted",
                                    actionLabel = "Undo"
                                )
                                // If we clicked on the SnackBar, lets restore the sleep
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(SleepsEvent.RestoreSleep)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
