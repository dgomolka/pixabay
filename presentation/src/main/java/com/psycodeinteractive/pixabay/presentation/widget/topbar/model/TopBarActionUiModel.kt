package com.psycodeinteractive.pixabay.presentation.widget.topbar.model

import androidx.annotation.StringRes

sealed class TopBarActionPresentationModel(
    @StringRes val textResource: Int = 0
) {
    object None : TopBarActionPresentationModel()
}
