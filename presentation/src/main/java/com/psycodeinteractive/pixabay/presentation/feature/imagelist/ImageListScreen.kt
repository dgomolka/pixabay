package com.psycodeinteractive.pixabay.presentation.feature.imagelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.psycodeinteractive.pixabay.presentation.R
import com.psycodeinteractive.pixabay.presentation.Screen
import com.psycodeinteractive.pixabay.presentation.extension.value
import com.psycodeinteractive.pixabay.presentation.feature.imagedetails.ImageDetailsPresentationDestination
import com.psycodeinteractive.pixabay.presentation.feature.imagedetails.model.ImagePresentationModel
import com.psycodeinteractive.pixabay.presentation.feature.imagelist.ImageListEvent.OpenShouldLaunchImageDetailsDialog
import com.psycodeinteractive.pixabay.presentation.feature.imagelist.model.ImageListTopBarResourcesPresentationModel
import com.psycodeinteractive.pixabay.presentation.observeWithLifecycle
import com.psycodeinteractive.pixabay.presentation.themeTypography
import com.psycodeinteractive.pixabay.presentation.widget.AcceptDeclineDialog
import com.psycodeinteractive.pixabay.presentation.widget.LoadingIndicator
import com.psycodeinteractive.pixabay.presentation.widget.image.PixabayImage
import com.psycodeinteractive.pixabay.presentation.widget.list.PixabayPagedLazyColumn
import com.psycodeinteractive.pixabay.presentation.widget.topbar.TopBar
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ImageListScreen(
    goToImageDetails: (id: Int) -> Unit
) {
    Screen<ImageListViewModel, _, _> { viewModel, viewState ->
        val imageList = remember(viewState.imageList) {
            viewState.imageList
        }.collectAsLazyPagingItems()

        ImageListContent(
            imageList = imageList,
            viewModel = viewModel,
            onImageClick = viewModel::onImageClick
        )

        HandleNavigation(viewModel, goToImageDetails)
        HandleEvents(viewModel)
    }
}

@Composable
private fun HandleNavigation(
    viewModel: ImageListViewModel,
    goToImageDetails: (id: Int) -> Unit
) {
    viewModel.navigationFlow.observeWithLifecycle { destination ->
        when (destination) {
            is ImageDetailsPresentationDestination -> goToImageDetails(destination.imageId)
        }
    }
}

@Composable
private fun HandleEvents(
    viewModel: ImageListViewModel
) {
    var imageIdToOpen by remember { mutableStateOf<Int?>(null) }
    imageIdToOpen?.run {
        OpenImageDetailsDialog(viewModel, this) {
            imageIdToOpen = null
        }
    }

    viewModel.eventFlow.observeWithLifecycle { event ->
        when (event) {
            is OpenShouldLaunchImageDetailsDialog -> imageIdToOpen = event.imageId
        }
    }
}

@Composable
private fun OpenImageDetailsDialog(
    viewModel: ImageListViewModel,
    imageId: Int,
    hideDialog: () -> Unit
) {
    AcceptDeclineDialog(
        title = R.string.open_details_screen.value,
        message = R.string.open_details_screen_description.value,
        onAcceptClick = {
            viewModel.onOpenImageDetailsAccepted(imageId)
        }
    ) { showDialog ->
        if (!showDialog) {
            hideDialog()
        }
    }
}

@Composable
private fun ImageListContent(
    imageList: LazyPagingItems<ImagePresentationModel>,
    viewModel: ImageListViewModel,
    onImageClick: (id: Int) -> Unit
) {
    val appendLoadState = imageList.loadState.append
    val refreshLoadState = imageList.loadState.refresh
    val isLoading = when {
        refreshLoadState is NotLoading && appendLoadState is NotLoading -> false
        refreshLoadState is Loading || appendLoadState is Loading -> true
        appendLoadState is Error || refreshLoadState is Error -> {
            val error = (appendLoadState as? Error ?: refreshLoadState as? Error)?.error
            LaunchedEffect(imageList) {
                viewModel.onError(error?.message.toString())
            }
            false
        } else -> false
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            resources = ImageListTopBarResourcesPresentationModel
        )
        Search(
            viewModel = viewModel
        )
        Divider(
            color = topDividerColor
        )

        if (isLoading) {
            LoadingIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .size(25.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else if (viewModel.currentViewState().currentQuery.isNotBlank()) {
            PixabayPagedLazyColumn(
                items = imageList,
                key = { _, item ->
                    item.id
                }
            ) { index, item ->
                ImageListItem(
                    image = item,
                    onClick = onImageClick
                )
            }
        }
    }
}

@Composable
private fun Search(
    viewModel: ImageListViewModel
) {
    var text by remember { mutableStateOf(viewModel.currentViewState().currentQuery) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background),
        value = text,
        onValueChange = { searchQuery ->
            text = searchQuery
            viewModel.onQueryChanged(searchQuery)
        },
        textStyle = themeTypography.body1,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        },
        label = {
            Text(R.string.search.value)
        },
        placeholder = {
            Text(R.string.please_enter_search_query.value)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        singleLine = true
    )
}

@Composable
private fun ImageListItem(
    image: ImagePresentationModel,
    onClick: (id: Int) -> Unit
) {
    val ripple = rememberRipple(
        color = MaterialTheme.colors.primary
    )
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = ripple,
            onClick = { onClick(image.id) }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = listItemPadding, end = listItemPadding, top = listItemPadding)
        ) {
            Row(
                modifier = Modifier.align(Start)
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        maxLines = 2,
                        overflow = Ellipsis,
                        text = "${R.string.user.value} ${image.user}",
                        style = themeTypography.body1
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        text = image.tags,
                        style = themeTypography.caption,
                        overflow = Ellipsis
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                PixabayImage(
                    modifier = Modifier.requiredSize(imageSize),
                    url = image.previewUrl
                )
            }

            Divider(
                modifier = Modifier.padding(top = listItemPadding),
                color = listItemDividerColor
            )
        }
    }
}

private val listItemPadding = 16.dp

private val imageSize = 50.dp

private val listItemDividerColor = Color.LightGray.copy(0.3f)
private val topDividerColor = Color.LightGray.copy(0.35f)
