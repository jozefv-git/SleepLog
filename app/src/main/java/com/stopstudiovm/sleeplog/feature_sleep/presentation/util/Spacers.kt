package com.stopstudiovm.sleeplog.feature_sleep.presentation.util

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stopstudiovm.sleeplog.ui.theme.SpacesShapes

// Created spacers for easier component management
@Composable
fun SpacerVerS(){
    Spacer(modifier = Modifier.height(SpacesShapes().spaces.spaceS))
}
@Composable
fun SpacerVerM(){
    Spacer(modifier = Modifier.height(SpacesShapes().spaces.spaceM))
}
@Composable
fun SpacerVerL(){
    Spacer(modifier = Modifier.height(SpacesShapes().spaces.spaceL))
}
@Composable
fun SpacerVerXL(){
    Spacer(modifier = Modifier.height(SpacesShapes().spaces.spaceXL))
}
@Composable
fun SpacerVerXXL(){
    Spacer(modifier = Modifier.height(SpacesShapes().spaces.spaceXXL))
}
@Composable
fun SpacerHorS(){
    Spacer(modifier = Modifier.width(SpacesShapes().spaces.spaceS))
}
@Composable
fun SpacerHorM(){
    Spacer(modifier = Modifier.width(SpacesShapes().spaces.spaceM))
}
@Composable
fun SpacerHorL(){
    Spacer(modifier = Modifier.width(SpacesShapes().spaces.spaceL))
}
@Composable
fun SpacerHorXL(){
    Spacer(modifier = Modifier.width(SpacesShapes().spaces.spaceXL))
}
@Composable
fun SpacerHorXXL(){
    Spacer(modifier = Modifier.width(SpacesShapes().spaces.spaceXXL))
}