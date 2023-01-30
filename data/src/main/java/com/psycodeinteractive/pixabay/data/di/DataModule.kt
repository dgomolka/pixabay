package com.psycodeinteractive.pixabay.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import com.psycodeinteractive.pixabay.data.model.ImagesPagingContainer
import com.psycodeinteractive.pixabay.data.repository.ImageDataRepository
import com.psycodeinteractive.pixabay.data.source.IMAGES_PAGE_SIZE
import com.psycodeinteractive.pixabay.data.source.ImageRemoteMediator
import com.psycodeinteractive.pixabay.data.source.local.AppDatabase
import com.psycodeinteractive.pixabay.data.source.local.ImageDao
import com.psycodeinteractive.pixabay.data.source.local.RemoteKeysDao
import com.psycodeinteractive.pixabay.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesImageDao(
        appDatabase: AppDatabase
    ) = appDatabase.imageDao()

    @Provides
    fun providesRemoteKeysDao(
        appDatabase: AppDatabase
    ) = appDatabase.remoteKeysDao()

    @ExperimentalPagingApi
    @Provides
    fun providesImagePagingContainer(
        config: PagingConfig,
        remoteMediator: ImageRemoteMediator,
        remoteKeysDao: RemoteKeysDao
    ) = ImagesPagingContainer(config, remoteMediator, remoteKeysDao)

    @Reusable
    @Provides
    fun providesPagingConfig() = PagingConfig(
        pageSize = IMAGES_PAGE_SIZE,
        initialLoadSize = IMAGES_PAGE_SIZE,
        prefetchDistance = IMAGES_PAGE_SIZE / 2
    )

    @Reusable
    @Provides
    fun providesImageRepository(
        imageDao: ImageDao,
        pagingContainer: ImagesPagingContainer
    ): ImageRepository = ImageDataRepository(
        imageDao,
        pagingContainer
    )
}
