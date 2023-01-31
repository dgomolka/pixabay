package com.psycodeinteractive.pixabay.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.psycodeinteractive.pixabay.data.model.local.ImageDatabaseModel

@Dao
interface ImageDao {
    @Query("SELECT * FROM image_table WHERE id = :id")
    suspend fun getById(id: Int): ImageDatabaseModel

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(imageList: List<ImageDatabaseModel>)
}
