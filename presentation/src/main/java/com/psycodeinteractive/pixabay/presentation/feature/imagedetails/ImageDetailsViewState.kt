package com.psycodeinteractive.pixabay.presentation.feature.imagedetails

import com.psycodeinteractive.pixabay.presentation.ViewState
import com.psycodeinteractive.pixabay.presentation.feature.imagedetails.model.ImagePresentationModel

data class ImageDetailsViewState(
    var image: ImagePresentationModel? = null
) : ViewState()
