package com.example.strengthtraningprogression.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeRangeSelector(
    selectedRange: String,
    onRangeSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    ranges: List<Pair<String, String>> = listOf(
        "week" to "Week",
        "month" to "Month",
        "year" to "Year"
    )
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ranges.forEach { (value, label) ->
            FilterChip(
                selected = selectedRange == value,
                onClick = { onRangeSelected(value) },
                label = { Text(label) }
            )
        }
    }
}
