package com.psycodeinteractive.pixabay.domain.usecase

import com.psycodeinteractive.pixabay.domain.repository.ImageRepository

class GetImageByIdUseCase(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(id: Int) = imageRepository.getImage(id)
}
