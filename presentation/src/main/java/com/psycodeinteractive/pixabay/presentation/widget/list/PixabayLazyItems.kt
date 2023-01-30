package com.psycodeinteractive.pixabay.presentation.widget.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.psycodeinteractive.pixabay.presentation.R
import com.psycodeinteractive.pixabay.presentation.extension.value
import com.psycodeinteractive.pixabay.presentation.themeTypography
import com.psycodeinteractive.pixabay.presentation.widget.list.LazyItemsArrangement.Horizontal
import com.psycodeinteractive.pixabay.presentation.widget.list.LazyItemsArrangement.Vertical
import androidx.paging.compose.itemsIndexed as pagingItemsIndexed

@Composable
fun <Model : Any> PixabayLazyRow(
    modifier: Modifier = Modifier,
    items: List<Model>,
    itemSpacing: Dp = defaultItemSpacing,
    key: ((index: Int, item: Model) -> Any)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    lazyListScope: LazyListScope.() -> Unit = {},
    lazyItemScope: @Composable LazyItemScope.(index: Int, item: Model) -> Unit = { _, _ -> }
) {
    ListContainer(modifier, items, lazyListScope, lazyItemScope, itemSpacing, key, contentPadding, Horizontal)
}

@Composable
fun <Model : Any> PixabayLazyColumn(
    modifier: Modifier = Modifier,
    items: List<Model>,
    itemSpacing: Dp = defaultItemSpacing,
    key: ((index: Int, item: Model) -> Any)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    lazyListScope: LazyListScope.() -> Unit = {},
    lazyItemScope: @Composable LazyItemScope.(index: Int, item: Model) -> Unit = { _, _ -> }
) {
    ListContainer(modifier, items, lazyListScope, lazyItemScope, itemSpacing, key, contentPadding, Vertical)
}

@Composable
fun <Model : Any> PixabayPagedLazyColumn(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<Model>,
    itemSpacing: Dp = defaultItemSpacing,
    key: ((index: Int, item: Model) -> Any)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    lazyListScope: LazyListScope.() -> Unit = {},
    lazyItemScope: @Composable LazyItemScope.(index: Int, item: Model) -> Unit = { _, _ -> }
) {
    PagedListContainer(modifier, items, lazyListScope, lazyItemScope, itemSpacing, key, contentPadding, Vertical)
}

@Composable
private fun <Model : Any> ListContainer(
    modifier: Modifier = Modifier,
    items: List<Model>,
    lazyListScope: LazyListScope.() -> Unit = {},
    lazyItemScope: @Composable LazyItemScope.(index: Int, item: Model) -> Unit = { _, _ -> },
    itemSpacing: Dp,
    key: ((index: Int, item: Model) -> Any)? = null,
    contentPadding: PaddingValues,
    arrangement: LazyItemsArrangement
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Crossfade(targetState = items.isEmpty()) { isEmpty ->
            when (isEmpty) {
                true -> EmptyListIndicator()
                false -> List(items, null, lazyListScope, lazyItemScope, itemSpacing, key, contentPadding, arrangement)
            }
        }
    }
}

@Composable
private fun <Model : Any> PagedListContainer(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<Model>,
    lazyListScope: LazyListScope.() -> Unit = {},
    lazyItemScope: @Composable LazyItemScope.(index: Int, item: Model) -> Unit = { _, _ -> },
    itemSpacing: Dp,
    key: ((index: Int, item: Model) -> Any)? = null,
    contentPadding: PaddingValues,
    arrangement: LazyItemsArrangement
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Crossfade(targetState = items.itemSnapshotList.isEmpty()) { isEmpty ->
            when (isEmpty) {
                true -> EmptyListIndicator()
                false -> List(null, items, lazyListScope, lazyItemScope, itemSpacing, key, contentPadding, arrangement)
            }
        }
    }
}

@Composable
private fun <Model : Any> List(
    items: List<Model>?,
    pagingItems: LazyPagingItems<Model>?,
    lazyListScope: LazyListScope.() -> Unit = {},
    lazyItemScope: @Composable LazyItemScope.(index: Int, item: Model) -> Unit = { _, _ -> },
    itemSpacing: Dp,
    key: ((index: Int, item: Model) -> Any)? = null,
    contentPadding: PaddingValues,
    arrangement: LazyItemsArrangement
) {
    val content: LazyListScope.() -> Unit = {
        if (items != null) {
            itemsIndexed(
                items = items,
                key = key
            ) { index: Int, item: Model ->
                lazyItemScope(index, item)
            }
        } else if (pagingItems != null) {
            pagingItemsIndexed(
                items = pagingItems,
                key = key
            ) { index: Int, item: Model? ->
                if (item != null) {
                    lazyItemScope(index, item)
                }
            }
        }
        lazyListScope()
    }
    val listState = pagingItems?.rememberLazyListState() ?: rememberLazyListState()

    val spacing = Arrangement.spacedBy(itemSpacing)
    when (arrangement) {
        Vertical -> LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = content,
            state = listState,
            contentPadding = contentPadding,
            verticalArrangement = spacing
        )
        Horizontal -> LazyRow(
            modifier = Modifier.fillMaxSize(),
            content = content,
            state = listState,
            contentPadding = contentPadding,
            horizontalArrangement = spacing
        )
    }
}

@Composable
private fun BoxScope.EmptyListIndicator() {
    Text(
        modifier = Modifier
            .padding(start = 16.dp)
            .align(Center),
        text = R.string.no_items.value,
        textAlign = TextAlign.Center,
        style = themeTypography.h5
    )
}

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState() = when (itemCount) {
    0 -> remember(this) { LazyListState(0, 0) }
    else -> androidx.compose.foundation.lazy.rememberLazyListState()
}

private enum class LazyItemsArrangement {
    Vertical, Horizontal
}

private val defaultItemSpacing = 10.dp
