package com.psycodeinteractive.pixabay.presentation.feature.imagelist

import com.psycodeinteractive.pixabay.presentation.Event

sealed class ImageListEvent : Event {
    class OpenShouldLaunchImageDetailsDialog(val imageId: Int) : ImageListEvent()
}
