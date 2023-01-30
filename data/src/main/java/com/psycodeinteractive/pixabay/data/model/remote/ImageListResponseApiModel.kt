package com.psycodeinteractive.pixabay.data.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class ImageListResponseApiModel(
    val totalHits: Int,
    val hits: List<ImageApiModel>
)
