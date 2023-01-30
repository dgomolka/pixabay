package com.psycodeinteractive.pixabay.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LabeledChip(
    modifier: Modifier = Modifier,
    labelText: String,
    valueText: String,
    smaller: Boolean = false,
    chipColorLight: Color = defaultChipColorLight,
    chipColorDark: Color = defaultChipColorDark
) {
    Row(
        modifier = modifier.clip(RoundedCornerShape(roundedCorner))
    ) {
        Text(
            modifier = Modifier
                .chipModifier(chipColorLight)
                .clip(RoundedCornerShape(topStart = roundedCorner, bottomStart = roundedCorner))
                .fillMaxHeight(),
            text = labelText,
            style = if (smaller) MaterialTheme.typography.overline else MaterialTheme.typography.subtitle2
        )
        Text(
            modifier = Modifier
                .chipModifier(chipColorDark)
                .clip(RoundedCornerShape(topEnd = roundedCorner, bottomEnd = roundedCorner))
                .fillMaxHeight(),
            text = valueText,
            style = if (smaller) MaterialTheme.typography.overline else MaterialTheme.typography.subtitle2
        )
    }
}

fun Modifier.chipModifier(
    color: Color,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp)
) = background(color, shape)
    .padding(horizontal = 6.dp, vertical = 3.dp)

private val defaultChipColorLight = Color.Gray.copy(0.35f)
private val defaultChipColorDark = Color.Gray.copy(0.8f)

private val roundedCorner = 6.dp
