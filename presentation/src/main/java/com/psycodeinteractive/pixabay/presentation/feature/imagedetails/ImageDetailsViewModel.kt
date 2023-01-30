package com.psycodeinteractive.pixabay.presentation.feature.imagedetails

import androidx.lifecycle.viewModelScope
import com.psycodeinteractive.pixabay.domain.Logger
import com.psycodeinteractive.pixabay.domain.model.ImageDomainModel
import com.psycodeinteractive.pixabay.domain.usecase.GetImageByIdUseCase
import com.psycodeinteractive.pixabay.presentation.BaseViewModel
import com.psycodeinteractive.pixabay.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val getImageByIdUseCase: GetImageByIdUseCase,
    override val logger: Logger
) : BaseViewModel<ImageDetailsViewState, ImageDetailsEvent>() {

    override val initialViewState = ImageDetailsViewState()

    fun fetchImageDetails(imageId: Int) {
        viewModelScope.launch {
            val image = getImageByIdUseCase(imageId)
            updateImageState(image)
        }
    }

    fun onViewCreated(imageId: Int) {
        fetchImageDetails(imageId)
    }

    private fun updateImageState(imageDomainModel: ImageDomainModel) {
        updateViewState {
            image = imageDomainModel.toPresentation()
        }
    }
}
