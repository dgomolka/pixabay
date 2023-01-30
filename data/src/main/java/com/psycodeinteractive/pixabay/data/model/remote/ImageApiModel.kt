package com.psycodeinteractive.pixabay.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageApiModel(
    val id: Int,
    @SerialName("pageURL")
    val pageUrl: String,
    val type: String,
    val tags: String,
    @SerialName("previewURL")
    val previewUrl: String,
    val previewWidth: Int,
    val previewHeight: Int,
    @SerialName("webformatURL")
    val webFormatUrl: String,
    @SerialName("webformatWidth")
    val webFormatWidth: Int,
    @SerialName("webformatHeight")
    val webFormatHeight: Int,
    @SerialName("largeImageURL")
    val largeImageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
    @SerialName("user_id")
    val userId: Int,
    val user: String,
    @SerialName("userImageURL")
    val userImageUrl: String
)
