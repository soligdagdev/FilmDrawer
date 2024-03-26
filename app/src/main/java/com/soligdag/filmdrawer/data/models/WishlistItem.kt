package com.soligdag.filmdrawer.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "WishList")
data class WishlistItem(
    @PrimaryKey var id: Int = 0,
    var voteAverage: Double = 0.0,
    var releaseDate: String = "",
    var posterPath: String = "",
    var title: String  ="",
    var mediaType: String = "",
    var overview : String = "",
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