package com.soligdag.filmdrawer.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MediaItem (
    @SerializedName("id")
    val id : Int,
    @SerializedName("vote_average")
    val voteAverage : Double,
    @SerializedName("release_date")
    val releaseDate : String,
    @SerializedName("poster_path")
    val posterPath : String = "",
    @SerializedName("title")
    val title : String = "",
    @SerializedName("name")
    val name : String = "",
    @SerializedName("media_type")
    var mediaType : String = "movie",
    @SerializedName("overview")
    val overview : String = "The story of J. Robert Oppenheimer’s role in the development of the atomic bomb during World War II.",
    ) {
    constructor(movie: MovieDetail) : this(id = movie.id?:0, voteAverage = movie.voteAverage?:0.0, releaseDate = movie.releaseDate?:"", posterPath = movie.posterPath?:"", title = movie.title?:"", name = movie.originalTitle?:"", mediaType = "movie", overview = movie.overview?:"")
    constructor(series: SeriesDetail) : this(id = series.id?:0, voteAverage = series.voteAverage?:0.0, releaseDate = series.firstAirDate?:"", posterPath = series.posterPath?:"", title = series.name?:"", name = series.name?:"", mediaType = "series", overview = series.overview?:"")
}