package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class ImageEntity(
    val descriptionShortUrl: String,
    val descriptionUrl: String,
    val mediaType: String,
    val mime: String,
    val timestamp: String,
    val url: String,
    val user: String,
    val userid: Int,
    @PrimaryKey
    val id: String,
)
