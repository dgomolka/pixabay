package com.psycodeinteractive.pixabay.presentation.widget.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.psycodeinteractive.pixabay.presentation.extension.value
import com.psycodeinteractive.pixabay.presentation.semiBold
import com.psycodeinteractive.pixabay.presentation.themeTypography
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.BackNavigationTypePresentationModel
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.TopBarActionPresentationModel
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.TopBarResourcesPresentationModel

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    resources: TopBarResourcesPresentationModel?,
    showAction: Boolean = true,
    onActionClick: ((action: TopBarActionPresentationModel) -> Unit)? = null,
    onBackNavigationClick: ((BackNavigationTypePresentationModel) -> Unit)? = null
) {
    resources?.run {
        if (titleBigTextResource == null && titleSmallTextResource == null) {
            return
        }

        val titleBigText = titleBigTextResource?.run { value }
        val titleSmallText = titleSmallTextResource?.run { value }

        val isTitleBigProvided = titleBigText != null

        val titleBigPadding = if (isTitleBigProvided) 12.dp else 0.dp

        val paddingModifier = Modifier.absolutePadding(
            top = titleBigPadding,
            bottom = titleBigPadding,
            left = 0.dp,
            right = 0.dp
        )

        TopAppBar(
            modifier = modifier
                then Modifier.background(MaterialTheme.colors.background)
                then paddingModifier,
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                NavigationButton(
                    type = backNavigationType,
                    onClick = onBackNavigationClick
                )
                Title(isTitleBigProvided, titleBigText, titleSmallText)
                Action(topBarAction, showAction, onActionClick)
            }
        }
    }
}

@Composable
private fun RowScope.Title(
    isTitleBigProvided: Boolean,
    titleBig: String?,
    titleSmall: String?
) {
    val titleTextStyle = if (isTitleBigProvided) themeTypography.h6 else themeTypography.subtitle1.semiBold()
    val titleText = (titleBig ?: titleSmall).orEmpty()
    Text(
        modifier = Modifier
            .padding(12.dp)
            .align(CenterVertically),
        text = titleText,
        color = MaterialTheme.colors.onBackground,
        style = titleTextStyle
    )
}

@Composable
private fun RowScope.Action(
    topBarAction: TopBarActionPresentationModel,
    showAction: Boolean,
    onActionClick: ((TopBarActionPresentationModel) -> Unit)?
) {
    if (topBarAction == TopBarActionPresentationModel.None) return

    val actionText = topBarAction.textResource.value
    AnimatedVisibility(
        modifier = Modifier
            .wrapContentSize()
            .align(CenterVertically),
        visible = showAction
    ) {
        Text(
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable {
                    onActionClick?.invoke(topBarAction)
                },
            text = actionText,
            maxLines = 1,
            color = MaterialTheme.colors.primary,
            style = themeTypography.subtitle1
        )
    }
}
