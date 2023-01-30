package com.psycodeinteractive.pixabay.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class ImageDatabaseModel(
    @PrimaryKey val id: Int,
    val pageUrl: String,
    val type: String,
    val tags: String,
    val previewUrl: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val webFormatUrl: String,
    val webFormatWidth: Int,
    val webFormatHeight: Int,
    val largeImageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
    val userId: Int,
    val user: String,
    val userImageUrl: String
)
