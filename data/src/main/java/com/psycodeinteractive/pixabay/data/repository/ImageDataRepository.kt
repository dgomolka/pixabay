package com.psycodeinteractive.pixabay.data.repository

import androidx.paging.flatMap
import androidx.paging.map
import com.psycodeinteractive.pixabay.data.model.ImagesPagingContainer
import com.psycodeinteractive.pixabay.data.model.toDomain
import com.psycodeinteractive.pixabay.data.source.local.ImageDao
import com.psycodeinteractive.pixabay.domain.repository.ImageRepository
import kotlinx.coroutines.flow.map

class ImageDataRepository(
    private val imageDao: ImageDao,
    private val imagesPagingContainer: ImagesPagingContainer
) : ImageRepository {
    override suspend fun getImageList(query: String) = imagesPagingContainer.createImagesPager(query)
        .flow
        .map { pagingData ->
            pagingData
                .flatMap { ids -> ids.split(",") }
                .map { id -> imageDao.getById(id.toInt()).toDomain() }
        }

    override suspend fun getImage(id: Int) = imageDao.getById(id).toDomain()
}
