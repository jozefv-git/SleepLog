package com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.stopstudiovm.sleeplog.feature_sleep.domain.model.Sleep
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SpacerVerXL
import com.stopstudiovm.sleeplog.ui.theme.DarkGray
import com.stopstudiovm.sleeplog.ui.theme.SpacesShapes

// Sleep List item
@Composable
fun SleepItem(
    modifier: Modifier = Modifier,
    sleep: Sleep,
    spacesShapes: SpacesShapes = SpacesShapes(),
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(sleep.color),
                    size = size,
                    cornerRadius = CornerRadius(spacesShapes.spaces.spaceL.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(sleep.color, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(spacesShapes.spaces.spaceL.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacesShapes.spaces.spaceXL)
                .padding(end = spacesShapes.spaces.spaceXXL)
        ) {
            Text(
                text = sleep.quality,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${sleep.sleepDurationHours} Hours ${sleep.sleepDurationMinutes} Minutes",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            SpacerVerXL()
            if (sleep.content.isNotEmpty()) {
                Text(
                    text = sleep.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            SpacerVerXL()
            Text(
                text = sleep.date,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete sleep",
                tint = DarkGray
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SleepItemPreview(){
//    //NoteItem(){}
//}