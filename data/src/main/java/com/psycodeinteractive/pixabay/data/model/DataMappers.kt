package com.psycodeinteractive.pixabay.data.model

import com.psycodeinteractive.pixabay.data.model.local.ImageDatabaseModel
import com.psycodeinteractive.pixabay.data.model.remote.ImageApiModel
import com.psycodeinteractive.pixabay.domain.model.ImageDomainModel

fun ImageApiModel.toDatabase() = ImageDatabaseModel(
    id,
    pageUrl,
    type,
    tags,
    previewUrl,
    previewWidth,
    previewHeight,
    webFormatUrl,
    webFormatWidth,
    webFormatHeight,
    largeImageUrl,
    imageWidth,
    imageHeight,
    imageSize,
    views,
    downloads,
    collections,
    likes,
    comments,
    userId,
    user,
    userImageUrl
)

fun ImageDatabaseModel.toDomain() = ImageDomainModel(
    id,
    pageUrl,
    type,
    tags,
    previewUrl,
    previewWidth,
    previewHeight,
    webFormatUrl,
    webFormatWidth,
    webFormatHeight,
    largeImageUrl,
    imageWidth,
    imageHeight,
    imageSize,
    views,
    downloads,
    collections,
    likes,
    comments,
    userId,
    user,
    userImageUrl
)

