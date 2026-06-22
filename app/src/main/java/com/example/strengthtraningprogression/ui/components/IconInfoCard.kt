package com.example.strengthtraningprogression.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// card with a leading emoji, title/subtitle, and an optional trailing slot.
// when onClick is provided the whole card is clickable.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconInfoCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    leadingEmoji: String = "🏋️",
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    val cardModifier = modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)

    val rowContent: @Composable () -> Unit = {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = leadingEmoji,
                modifier = Modifier.padding(end = 12.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            trailing?.invoke()
        }
    }

    if (onClick != null) {
        Card(
            modifier = cardModifier,
            onClick = onClick,
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            rowContent()
        }
    } else {
        Card(
            modifier = cardModifier,
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            rowContent()
        }
    }
}
