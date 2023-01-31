package com.psycodeinteractive.pixabay.presentation.feature.imagedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle.State.CREATED
import com.psycodeinteractive.pixabay.presentation.OnLifecycle
import com.psycodeinteractive.pixabay.presentation.R
import com.psycodeinteractive.pixabay.presentation.Screen
import com.psycodeinteractive.pixabay.presentation.bold
import com.psycodeinteractive.pixabay.presentation.extension.value
import com.psycodeinteractive.pixabay.presentation.feature.imagedetails.model.ImageDetailsTopBarResourcesPresentationModel
import com.psycodeinteractive.pixabay.presentation.feature.imagedetails.model.ImagePresentationModel
import com.psycodeinteractive.pixabay.presentation.navigation.BackDestination
import com.psycodeinteractive.pixabay.presentation.observeWithLifecycle
import com.psycodeinteractive.pixabay.presentation.widget.LabeledChip
import com.psycodeinteractive.pixabay.presentation.widget.image.PixabayImage
import com.psycodeinteractive.pixabay.presentation.widget.topbar.TopBar
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.BackNavigationTypePresentationModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ImageDetailsScreen(
    imageId: Int,
    onCloseClick: () -> Unit
) {
    Screen<ImageDetailsViewModel, _, _> { viewModel, viewState ->
        OnLifecycle(minActiveState = CREATED) {
            viewModel.onViewCreated(imageId)
        }

        ImageDetailsScreenContent(viewState.image) {
            viewModel.onCloseAction()
        }

        HandleNavigation(viewModel, onCloseClick)
    }
}

@Composable
fun HandleNavigation(
    viewModel: ImageDetailsViewModel,
    onCloseClick: () -> Unit
) {
    viewModel.navigationFlow.observeWithLifecycle { destination ->
        when (destination) {
            BackDestination -> onCloseClick()
        }
    }
}

@Composable
private fun ImageDetailsScreenContent(
    image: ImagePresentationModel?,
    onBackNavigationClick: (BackNavigationTypePresentationModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            resources = ImageDetailsTopBarResourcesPresentationModel,
            onBackNavigationClick = onBackNavigationClick
        )
        Divider(
            color = topDividerColor
        )
        ImageDetails(image)
    }
}

@Composable
private fun ImageDetails(image: ImagePresentationModel?) {
    image?.run {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(columnItemsSpacing)
        ) {
            PixabayImage(
                modifier = Modifier.aspectRatio(16 / 9f),
                url = image.largeImageUrl
            )
            User("${R.string.user.value} ${image.user}")
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                LabeledChip(
                    labelText = R.string.likes.value,
                    valueText = "${image.likes}",
                    smaller = true
                )
                LabeledChip(
                    labelText = R.string.downloads.value,
                    valueText = "${image.downloads}",
                    smaller = true
                )
                LabeledChip(
                    labelText = R.string.comments.value,
                    valueText = "${image.comments}",
                    smaller = true
                )
            }
            Label(image.tags)
        }
    }
}

@Composable
private fun User(user: String) {
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = user,
        style = MaterialTheme.typography.h6.bold()
    )
}

@Composable
private fun Label(title: String) {
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = title,
        style = MaterialTheme.typography.subtitle2
    )
}

private val columnItemsSpacing = 10.dp
private val topDividerColor = Color.LightGray.copy(0.35f)
