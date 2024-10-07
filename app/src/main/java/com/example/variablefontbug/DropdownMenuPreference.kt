package com.example.variablefontbug

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuPreference(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    value: String,
    clickable: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    dropdownMenuOptions: List<String>,
    onDropdownMenuItemSelected: (Int) -> Unit
) {
    var dropdownMenuExpanded by remember { mutableStateOf(false) }
    Preference(
        modifier = modifier,
        title = title,
        subtitle = subtitle,
        clickable = clickable,
        paddingValues = paddingValues,
        onClick = { dropdownMenuExpanded = true }
    ) {
        Box {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = if (clickable) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
            DropdownMenu(
                expanded = dropdownMenuExpanded,
                onDismissRequest = { dropdownMenuExpanded = false }) {
                dropdownMenuOptions.forEachIndexed { index, it ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = it
                            )
                        },
                        onClick = {
                            onDropdownMenuItemSelected(index)
                            dropdownMenuExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    icon: @Composable (() -> Unit?)? = null,
    clickable: Boolean = true,
    onClick: () -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    content: @Composable (() -> Unit)? = null
) {
    val clickableModifier = if (clickable) Modifier.clickable(onClick = onClick) else Modifier
    val textColor =
        if (clickable) MaterialTheme.colorScheme.onSurface
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    val subtitleColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.then(clickableModifier).padding(paddingValues)
    ) {
        icon?.let {
            Box(modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp)) {
                it()
            }
        }
        Column(modifier = Modifier.weight(1f).padding(vertical = 8.dp), horizontalAlignment = Alignment.Start) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (clickable) subtitleColor else textColor
                )
            }
        }
        content?.let { it() }
    }
}