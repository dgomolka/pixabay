package com.psycodeinteractive.pixabay.presentation.feature.imagedetails.model

data class ImagePresentationModel(
    val id: Int,
    val type: String,
    val tags: String,
    val previewUrl: String,
    val largeImageUrl: String,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
    val user: String
)
