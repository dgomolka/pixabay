package com.psycodeinteractive.pixabay.domain.di

import com.psycodeinteractive.pixabay.domain.repository.ImageRepository
import com.psycodeinteractive.pixabay.domain.usecase.GetImageByIdUseCase
import com.psycodeinteractive.pixabay.domain.usecase.GetImageListByQueryUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Reusable
    @Provides
    fun providesGetImageListByQueryUseCase(
        imageRepository: ImageRepository
    ) = GetImageListByQueryUseCase(imageRepository)

    @Reusable
    @Provides
    fun providesGetImageByIdUseCase(
        imageRepository: ImageRepository
    ) = GetImageByIdUseCase(imageRepository)
}
