package com.stopstudiovm.sleeplog.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SpacesShapes(
    val shapes: Shapes = Shapes(),
    val spaces: Spaces = Spaces()
)

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp)
)
data class Spaces(
    val spaceS: Dp = 4.dp,
    val spaceM: Dp = 8.dp,
    val spaceL: Dp = 12.dp,
    val spaceXL: Dp = 16.dp,
    val spaceXXL: Dp = 32.dp,
)