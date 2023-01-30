package com.psycodeinteractive.pixabay.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.psycodeinteractive.pixabay.data.source.ImageRemoteMediator
import com.psycodeinteractive.pixabay.data.source.local.AppDatabase
import com.psycodeinteractive.pixabay.data.source.remote.PixabayApiService
import com.psycodeinteractive.pixabay.domain.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).setQueryCallback(
        queryCallback = object : RoomDatabase.QueryCallback {
            override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                println("SQL Query: $sqlQuery SQL Args: $bindArgs")
            }
        },
        executor = Executors.newSingleThreadExecutor()
    ).build()

    @Provides
    @Singleton
    fun providesRemoteMediator(
        appDatabase: AppDatabase,
        pixabayApiService: PixabayApiService,
        logger: Logger
    ) = ImageRemoteMediator(
        appDatabase,
        pixabayApiService,
        logger
    )
}
