package com.psycodeinteractive.pixabay.presentation.model

import com.psycodeinteractive.pixabay.domain.model.ImageDomainModel
import com.psycodeinteractive.pixabay.presentation.feature.imagedetails.model.ImagePresentationModel

fun ImageDomainModel.toPresentation() = ImagePresentationModel(
    id,
    type,
    tags,
    previewUrl,
    largeImageUrl,
    views,
    downloads,
    collections,
    likes,
    comments,
    user
)
