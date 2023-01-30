package com.psycodeinteractive.pixabay.domain.repository

import androidx.paging.PagingData
import com.psycodeinteractive.pixabay.domain.model.ImageDomainModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getImageList(query: String): Flow<PagingData<ImageDomainModel>>
    suspend fun getImage(id: Int): ImageDomainModel
}
