package com.psycodeinteractive.pixabay.domain.usecase

import com.psycodeinteractive.pixabay.domain.repository.ImageRepository

class GetImageListByQueryUseCase(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(query: String) = imageRepository.getImageList(query)
}
