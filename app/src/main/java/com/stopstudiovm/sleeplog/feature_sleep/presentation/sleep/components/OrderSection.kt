package com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.OrderType
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SleepOrder

@Composable
fun OrderSection(
    modifier: Modifier,
    sleepOrder: SleepOrder = SleepOrder.Date(OrderType.Descending),
    onOrderChange: (SleepOrder) -> Unit
){
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Date",
                selected = sleepOrder is SleepOrder.Date,
                onSelect = { onOrderChange(SleepOrder.Date(sleepOrder.orderType))}
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Quality",
                selected = sleepOrder is SleepOrder.Color,
                onSelect = { onOrderChange(SleepOrder.Color(sleepOrder.orderType))}
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = sleepOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(sleepOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = sleepOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(sleepOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}