package com.soligdag.filmdrawer.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "WishList")
data class WishlistItem(
    @PrimaryKey val id: Int,
    val voteAverage: Double,
    val releaseDate: String,
    val posterPath: String,
    val title: String,
    val mediaType: String,
    val overview : String,
    ) {
    constructor(mediaItem: MediaItem) : this(
        id = mediaItem.id,
        voteAverage = mediaItem.voteAverage,
        releaseDate = mediaItem.releaseDate,
        posterPath = mediaItem.posterPath,
        title = if(mediaItem.mediaType == "movie") mediaItem.title else mediaItem.name,
        mediaType = mediaItem.mediaType,
        overview = mediaItem.overview
    )
}