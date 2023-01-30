package com.psycodeinteractive.pixabay.presentation.widget.topbar.model

import androidx.annotation.DrawableRes
import com.psycodeinteractive.pixabay.presentation.R

sealed class BackNavigationTypePresentationModel(
    @DrawableRes val iconResource: Int = 0
) {
    object Close : BackNavigationTypePresentationModel(iconResource = R.drawable.icon_close)
    object None : BackNavigationTypePresentationModel()
}
