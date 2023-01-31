package com.psycodeinteractive.pixabay.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.MediatorResult.Error
import androidx.paging.RemoteMediator.MediatorResult.Success
import androidx.room.withTransaction
import com.psycodeinteractive.pixabay.data.BuildConfig
import com.psycodeinteractive.pixabay.data.model.ImagesPagingSource
import com.psycodeinteractive.pixabay.data.model.local.RemoteKeyDatabaseModel
import com.psycodeinteractive.pixabay.data.model.remote.ImageApiModel
import com.psycodeinteractive.pixabay.data.model.toDatabase
import com.psycodeinteractive.pixabay.data.source.local.AppDatabase
import com.psycodeinteractive.pixabay.data.source.remote.PixabayApiService
import com.psycodeinteractive.pixabay.domain.Logger
import okio.IOException
import retrofit2.HttpException
import kotlin.math.floor

const val IMAGES_PAGE_SIZE = 100

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
    private val appDatabase: AppDatabase,
    private val pixabayApiService: PixabayApiService,
    private val logger: Logger
) : RemoteMediator<Int, String>() {
    private var imagesPagingSource: ImagesPagingSource? = null
    private val imageDao = appDatabase.imageDao()
    private val remoteKeysDao = appDatabase.remoteKeysDao()

    private var queryChanged = false

    var query: String = ""
        set(value) {
            field = value
            queryChanged = true
        }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, String>
    ): MediatorResult {
        return try {
            val remoteKey = remoteKeysDao.remoteKeysByQuery(query)
            val previousIds = remoteKey?.ids?.split(",").orEmpty()

            val loadKey = when (if (queryChanged) REFRESH else loadType) {
                REFRESH -> 1
                PREPEND -> return Success(endOfPaginationReached = true)
                APPEND -> {
                    if (remoteKey == null) {
                        return Success(endOfPaginationReached = false)
                    }
                    val pagesLoaded = getPageFromCount(previousIds.size)
                    val totalPages = getPageFromCount(remoteKey.totalCount)
                    if (pagesLoaded == totalPages) {
                        return Success(endOfPaginationReached = true)
                    }
                    (pagesLoaded + 1).toInt()
                }
            }

            queryChanged = false

            val response = pixabayApiService.getImages(
                apiKey = BuildConfig.PixabayApiKey,
                query = query,
                page = loadKey,
                pageSize = IMAGES_PAGE_SIZE
            )

            val imageList = response.hits
            val updatedIds = previousIds.map { it.toInt() }.distinct() + imageList.map(ImageApiModel::id).distinct()

            appDatabase.withTransaction {
                val newRemoteKey = RemoteKeyDatabaseModel(query, updatedIds.joinToString(separator = ","), response.totalHits)
                remoteKeysDao.insertOrReplace(newRemoteKey)

                imageDao.insertAll(
                    imageList.map(ImageApiModel::toDatabase)
                )
            }

            imagesPagingSource?.invalidate()

            Success(endOfPaginationReached = updatedIds.size == response.totalHits)
        } catch (e: Exception) {
            logger.e(e)
            when (e) {
                is IOException, is HttpException -> Error(e)
                else -> throw e
            }
        }
    }

    private fun getPageFromCount(count: Int) = floor(count.toDouble() / IMAGES_PAGE_SIZE)

    fun attachPagingSource(imagesPagingSource: ImagesPagingSource) {
        this.imagesPagingSource = imagesPagingSource
    }
}
