package com.psycodeinteractive.pixabay.presentation.feature.imagelist

import androidx.paging.PagingData
import com.psycodeinteractive.pixabay.presentation.ViewState
import com.psycodeinteractive.pixabay.presentation.feature.imagedetails.model.ImagePresentationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ImageListViewState(
    var imageList: Flow<PagingData<ImagePresentationModel>> = emptyFlow(),
    var currentQuery: String = "fruits"
) : ViewState()
