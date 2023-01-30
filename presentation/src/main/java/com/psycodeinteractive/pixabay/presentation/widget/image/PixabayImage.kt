package com.psycodeinteractive.pixabay.presentation.widget.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale.Companion.FillHeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.psycodeinteractive.pixabay.presentation.R
import com.psycodeinteractive.pixabay.presentation.widget.LoadingIndicator
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun PixabayImage(
    modifier: Modifier = Modifier,
    url: String
) {
    BoxWithConstraints(modifier = modifier) {
        val context = LocalContext.current
        val localDensity = LocalDensity.current
        val maxWidth = with(localDensity) { maxWidth.toPx() }.toInt()
        val maxHeight = with(localDensity) { maxHeight.toPx() }.toInt()
        CoilImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(imageCornerRadius)),
            imageRequest = {
                ImageRequest.Builder(context)
                    .data(url)
                    .size(maxWidth, maxHeight)
                    .build()
            },
            imageOptions = ImageOptions(
                contentScale = FillHeight
            ),
            loading = {
                ImageLoading()
            },
            failure = {
                ImageFetchFailure()
            }
        )
    }
}

@Composable
private fun BoxScope.ImageFetchFailure() {
    Image(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(ratio = 1.0f, matchHeightConstraintsFirst = true)
            .size(imageFetchStateIndicatorsSize)
            .align(Center),
        painter = painterResource(id = R.drawable.image_broken),
        contentDescription = "ImageFetchFailure",
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
    )
}

@Composable
private fun BoxScope.ImageLoading() {
    LoadingIndicator(
        modifier = Modifier
            .size(imageFetchStateIndicatorsSize)
            .align(Center)
    )
}

private val imageFetchStateIndicatorsSize = 60.dp
private val imageCornerRadius = 12.dp
