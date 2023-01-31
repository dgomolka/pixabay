package com.psycodeinteractive.pixabay.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.psycodeinteractive.pixabay.data.model.local.ImageDatabaseModel
import com.psycodeinteractive.pixabay.data.model.local.RemoteKeyDatabaseModel

@Database(
    entities = [
        ImageDatabaseModel::class,
        RemoteKeyDatabaseModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
