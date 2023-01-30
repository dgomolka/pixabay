package com.psycodeinteractive.pixabay.data.repository

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.flatMap
import androidx.paging.map
import com.psycodeinteractive.pixabay.data.model.ImagesPagingContainer
import com.psycodeinteractive.pixabay.data.model.toDomain
import com.psycodeinteractive.pixabay.data.source.local.ImageDao
import com.psycodeinteractive.pixabay.domain.model.ImageDomainModel
import com.psycodeinteractive.pixabay.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class ImageDataRepository(
    private val imageDao: ImageDao,
    private val imagesPagingContainer: ImagesPagingContainer
) : ImageRepository {
    override suspend fun getImageList(query: String): Flow<PagingData<ImageDomainModel>> {
        imagesPagingContainer.remoteMediator.query = query
        return imagesPagingContainer.imagesPager(query).flow
            .filterNotNull()
            .map { pagingData ->
                println("LOL $pagingData")
                pagingData
                    .map { ids -> ids.split(",") }
                    .flatMap { it }
                    .filter { it.isNotBlank() }
                    .map { id -> imageDao.getById(id.toInt()).toDomain() }
            }
    }

    override suspend fun getImage(id: Int) = imageDao.getById(id).toDomain()
}
