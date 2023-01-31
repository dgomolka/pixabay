package com.psycodeinteractive.pixabay.data.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.psycodeinteractive.pixabay.data.source.ImageRemoteMediator
import com.psycodeinteractive.pixabay.data.source.local.RemoteKeysDao

typealias ImagesPager = Pager<Int, String>
typealias ImagesPagingSource = PagingSource<Int, String>

data class ImagesPagingContainer(
    private val config: PagingConfig,
    val remoteMediator: ImageRemoteMediator,
    private val remoteKeysDao: RemoteKeysDao
) {
    @OptIn(ExperimentalPagingApi::class)
    fun createImagesPager(query: String) = ImagesPager(
        config = config,
        remoteMediator = remoteMediator.apply { this.query = query },
        pagingSourceFactory = {
            remoteKeysDao.remoteKeysByQueryPagingSource(query).apply {
                remoteMediator.attachPagingSource(this)
            }
        }
    )
}
