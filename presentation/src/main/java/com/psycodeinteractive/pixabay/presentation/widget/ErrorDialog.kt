package com.psycodeinteractive.pixabay.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.psycodeinteractive.pixabay.presentation.R
import com.psycodeinteractive.pixabay.presentation.extension.value

@Composable
fun ErrorDialog(
    title: String,
    message: String,
    setShowDialog: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        var surfaceHeight by remember { mutableStateOf(0) }
        Surface(
            modifier = Modifier
                .onSizeChanged { surfaceHeight = it.height }
                .wrapContentHeight()
                .fillMaxWidth(0.9f),
            shape = dialogShape,
            color = errorColor
        ) {
            Row(
                modifier = Modifier
                    .padding(start = startColoredSpacerWidth)
                    .background(MaterialTheme.colors.secondary)
                    .fillMaxWidth(0.8f)
                    .padding(end = contentPadding)
                    .wrapContentHeight(unbounded = true)
            ) {
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .align(CenterVertically),
                    imageVector = Icons.Rounded.Close,
                    contentDescription = Icons.Rounded.Close.name,
                    tint = errorColor
                )
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = message,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Text(
                    modifier = Modifier
                        .align(CenterVertically)
                        .fillMaxHeight()
                        .clickable {
                            setShowDialog(false)
                        },
                    text = R.string.close.value,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private val errorColor = Color.Red.copy(0.7f)
private val iconSize = 30.dp
private val startColoredSpacerWidth = 10.dp
private val contentPadding = 16.dp
private val dialogShape = RoundedCornerShape(16.dp)
