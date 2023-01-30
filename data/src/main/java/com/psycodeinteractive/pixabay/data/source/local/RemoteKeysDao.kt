package com.psycodeinteractive.pixabay.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.psycodeinteractive.pixabay.data.model.local.RemoteKeyDatabaseModel

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyDatabaseModel)

    @Query("SELECT * FROM remote_keys WHERE :query = `query`")
    suspend fun remoteKeysByQuery(query: String): RemoteKeyDatabaseModel?

    @Query("SELECT ids FROM remote_keys WHERE :query = `query`")
    fun remoteKeysByQueryPagingSource(query: String): PagingSource<Int, String>

    @Query("DELETE FROM remote_keys WHERE :query = 'query'")
    suspend fun deleteAll(query: String)
}
