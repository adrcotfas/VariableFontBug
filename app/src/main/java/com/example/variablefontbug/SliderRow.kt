package com.example.variablefontbug

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun SliderRow(
    title: String? = null,
    icon: @Composable (() -> Unit)? = null,
    value: Int,
    min: Int = 1,
    max: Int,
    steps: Int = max - min - 1,
    showSteps: Boolean = false,
    onValueChange: (Int) -> Unit,
    onValueChangeFinished: () -> Unit = { },
    onClick: (() -> Unit)? = null,
    valueNames: List<String>? = null,
    showValue: Boolean = true
) {
    val (sliderWeight, textWeight) = if (valueNames != null) {
        0.8f to 0.2f
    } else {
        0.9f to 0.1f
    }
    val valueText = if (valueNames != null) valueNames[value] else value.toString()

    Column(modifier = Modifier
        .fillMaxWidth()
        .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
        .padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        title?.let {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val colors = if (showSteps) SliderDefaults.colors()
            else SliderDefaults.colors().copy(
                inactiveTickColor = MaterialTheme.colorScheme.surfaceVariant,
                activeTickColor = MaterialTheme.colorScheme.primary
            )

            icon?.let {
                Box(modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp)) {
                    it()
                }
            }
            Slider(
                modifier = Modifier.weight(sliderWeight),
                colors = colors,
                value = value.toFloat(),
                onValueChange = {
                    onValueChange(it.roundToInt())
                },
                onValueChangeFinished = onValueChangeFinished,
                steps = steps,
                valueRange = min.toFloat()..max.toFloat()
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (showValue) {
                Text(
                    modifier = Modifier.weight(textWeight),
                    text = valueText,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}