@file:OptIn(FlowPreview::class)

package com.psycodeinteractive.pixabay.presentation.feature.imagelist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.psycodeinteractive.pixabay.domain.Logger
import com.psycodeinteractive.pixabay.domain.model.ImageDomainModel
import com.psycodeinteractive.pixabay.domain.usecase.GetImageListByQueryUseCase
import com.psycodeinteractive.pixabay.presentation.BaseViewModel
import com.psycodeinteractive.pixabay.presentation.feature.imagedetails.ImageDetailsPresentationDestination
import com.psycodeinteractive.pixabay.presentation.feature.imagelist.ImageListEvent.OpenShouldLaunchImageDetailsDialog
import com.psycodeinteractive.pixabay.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val QUERY_DEBOUNCE_MILLISECONDS = 500L

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getImageListByQueryUseCase: GetImageListByQueryUseCase,
    override val logger: Logger
) : BaseViewModel<ImageListViewState, ImageListEvent>() {

    override val initialViewState = ImageListViewState()

    private val searchFlow = MutableStateFlow("")

    private var isImageListFetched = false

    init {
        viewModelScope.launch {
            searchFlow
                .filter { it.isNotBlank() }
                .debounce(QUERY_DEBOUNCE_MILLISECONDS)
                .collect {
                    fetchImageList()
                }
        }
    }

    override fun onViewCreated() {
        if (!isImageListFetched) {
            fetchImageList()
        }
    }

    fun onQueryChanged(query: String) {
        updateViewState {
            currentQuery = query
        }
        searchFlow.value = query
    }

    private fun fetchImageList() {
        viewModelScope.launch {
            val imageList = getImageListByQueryUseCase(currentViewState().currentQuery)
            updateImageListState(imageList)
            isImageListFetched = true
        }
    }

    private fun updateImageListState(imagePagingData: Flow<PagingData<ImageDomainModel>>) {
        updateViewState {
            imageList = imagePagingData.map { pagingData ->
                pagingData.map(ImageDomainModel::toPresentation)
            }.cachedIn(viewModelScope)
        }
    }

    fun onImageClick(imageId: Int) {
        OpenShouldLaunchImageDetailsDialog(imageId).dispatchEvent()
    }

    fun onOpenImageDetailsAccepted(imageId: Int) {
        ImageDetailsPresentationDestination(imageId).navigate()
    }
}
