package com.psycodeinteractive.pixabay.presentation.widget.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import androidx.compose.ui.res.painterResource
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.BackNavigationTypePresentationModel
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.BackNavigationTypePresentationModel.Close
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.BackNavigationTypePresentationModel.None

@Composable
fun NavigationButton(
    modifier: Modifier = Modifier,
    type: BackNavigationTypePresentationModel = Close,
    onClick: ((BackNavigationTypePresentationModel) -> Unit)?
) {
    if (type == None) return

    Row(
        modifier = modifier,
        verticalAlignment = CenterVertically
    ) {
        IconButton(onClick = { onClick?.invoke(type) }) {
            Image(
                modifier = Modifier.graphicsLayer(rotationZ = if (type == Close) 45f else 0f),
                contentDescription = "Navigation Icon",
                painter = painterResource(id = type.iconResource),
                contentScale = Fit,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
            )
        }
    }
}
