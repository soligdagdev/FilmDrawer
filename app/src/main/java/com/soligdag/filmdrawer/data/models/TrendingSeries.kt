package com.soligdag.filmdrawer.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TrendingSeries")
data class TrendingSeries(
    @PrimaryKey(autoGenerate = true) val trendingSeriesId: Int = 0,
    @Embedded
    val mediaItem : MediaItem,
    val createdAt : Long
) {
//    constructor(mediaItem: MediaItem) : this(
//        id = mediaItem.id,
//        voteAverage = mediaItem.voteAverage,
//        releaseDate = mediaItem.releaseDate,
//        posterPath = mediaItem.posterPath,
//        title = if(mediaItem.mediaType == "movie") mediaItem.title else mediaItem.name,
//        mediaType = mediaItem.mediaType,
//        overview = mediaItem.overview
//    )
}