package com.stopstudiovm.sleeplog.feature_sleep.presentation.sleep.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.OrderType
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SleepOrder
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SpacerHorM
import com.stopstudiovm.sleeplog.feature_sleep.presentation.util.SpacerVerXL

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
            SpacerHorM()
            DefaultRadioButton(
                text = "Quality",
                selected = sleepOrder is SleepOrder.Color,
                onSelect = { onOrderChange(SleepOrder.Color(sleepOrder.orderType))}
            )
        }
        SpacerVerXL()
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
            SpacerHorM()
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