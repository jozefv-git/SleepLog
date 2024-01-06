package com.stopstudiovm.sleeplog.feature_sleep.presentation.add_edit_sleep.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

// Rounded button
@Composable
fun RoundedButton(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    background: Color,
    borderColor: Color,
    onClick: () -> Unit
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(56.dp)
            .shadow(15.dp, CircleShape)
            .clip(CircleShape)
            .background(background)
            .border(
                width = 3.dp,
                color = borderColor,
                shape = CircleShape
            )
            .clickable {
                onClick()
            }
    ){
        Text(
            text = text,
            style = textStyle,
            color = textColor)
    }
}