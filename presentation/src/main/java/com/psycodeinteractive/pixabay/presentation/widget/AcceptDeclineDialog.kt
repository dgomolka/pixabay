package com.psycodeinteractive.pixabay.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.rounded.Info
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
fun AcceptDeclineDialog(
    title: String,
    message: String,
    onDeclineClick: () -> Unit = {},
    onAcceptClick: () -> Unit = {},
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
            color = infoColor
        ) {
            Row(
                modifier = Modifier
                    .padding(start = startColoredSpacerWidth)
                    .background(MaterialTheme.colors.secondary)
                    .fillMaxWidth()
                    .padding(end = contentPadding)
                    .wrapContentHeight(unbounded = true)
            ) {
                Icon(
                    modifier = Modifier
                        .size(iconSize)
                        .align(CenterVertically),
                    imageVector = Icons.Rounded.Info,
                    contentDescription = Icons.Rounded.Info.name,
                    tint = infoColor
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
                    Row(
                        modifier = Modifier.wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .clickable {
                                    setShowDialog(false)
                                    onDeclineClick()
                                },
                            text = R.string.no.value,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .clickable {
                                    setShowDialog(false)
                                    onAcceptClick()
                                },
                            text = R.string.yes.value,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private val infoColor = Color.LightGray
private val iconSize = 30.dp
private val startColoredSpacerWidth = 10.dp
private val contentPadding = 16.dp
private val dialogShape = RoundedCornerShape(16.dp)
