package com.psycodeinteractive.pixabay.presentation.widget

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
            .aspectRatio(ratio = 1.0f, matchHeightConstraintsFirst = true),
        color = MaterialTheme.colors.onSurface
    )
}
