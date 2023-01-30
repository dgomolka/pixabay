package com.psycodeinteractive.pixabay.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyDatabaseModel(
    @PrimaryKey
    val query: String,
    val ids: String,
    val totalCount: Int
)
