package com.psycodeinteractive.pixabay.presentation.feature.imagedetails.model

import com.psycodeinteractive.pixabay.presentation.R
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.BackNavigationTypePresentationModel
import com.psycodeinteractive.pixabay.presentation.widget.topbar.model.TopBarResourcesPresentationModel

object ImageDetailsTopBarResourcesPresentationModel : TopBarResourcesPresentationModel(
    titleSmallTextResource = R.string.image_details,
    backNavigationType = BackNavigationTypePresentationModel.Close
)
