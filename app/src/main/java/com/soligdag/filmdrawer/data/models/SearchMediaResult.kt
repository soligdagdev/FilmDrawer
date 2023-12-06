package com.soligdag.filmdrawer.data.models

import com.google.gson.annotations.SerializedName

data class SearchMediaResult(
    val page : Int,
    val results : List<MediaItem>,
    @SerializedName("total_pages")
    val totalPages : Int,
    @SerializedName("total_results")
    val totalResults : Int
)