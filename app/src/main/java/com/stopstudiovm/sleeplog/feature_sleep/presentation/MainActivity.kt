package com.stopstudiovm.sleeplog.feature_sleep.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep.AddEditSleepScreen
import com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep.SleepsScreen
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.Screen
import com.stopstudiovm.sleeplog.ui.theme.SleepLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activity = LocalContext.current as AppCompatActivity
            SleepLogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SleepsScreen.route
                    ){
                        // Here we can define our different screens
                        composable(route = Screen.SleepsScreen.route){
                            SleepsScreen(navController = navController)
                        }
                        // Pass the arguments between the screens
                        composable(route = Screen.AddEditSleepScreen.route +
                        "?sleepId={sleepId}&sleepColor={sleepColor}",
                        arguments = listOf(
                            navArgument(
                                name = "sleepId"
                            ){ // Builder
                                type = NavType.IntType
                                defaultValue = -1 // We are using this value in AddEditNoteScreen and viewModels
                            },
                            navArgument(
                                name = "sleepColor"
                            ){ // Builder
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )){
                            // We want to get Int with selected key
                            val color = it.arguments?.getInt("sleepColor") ?: -1
                            AddEditSleepScreen(navController = navController, sleepColor = color, activity = activity)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SleepLogTheme {
        Greeting("Android")
    }
}