package com.psycodeinteractive.pixabay.data.source.remote

import com.psycodeinteractive.pixabay.data.model.remote.ImageListResponseApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {
    @GET("api")
    suspend fun getImages(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): ImageListResponseApiModel
}
