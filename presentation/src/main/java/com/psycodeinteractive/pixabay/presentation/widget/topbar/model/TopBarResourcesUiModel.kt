package com.psycodeinteractive.pixabay.presentation.widget.topbar.model

import androidx.annotation.StringRes
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.BackNavigationTypePresentationModel.None

abstract class TopBarResourcesPresentationModel(
    @StringRes val titleBigTextResource: Int? = null,
    @StringRes val titleSmallTextResource: Int? = null,
    val topBarAction: TopBarActionPresentationModel = TopBarActionPresentationModel.None,
    val backNavigationType: BackNavigationTypePresentationModel = None
)
