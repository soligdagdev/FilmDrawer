package com.soligdag.filmdrawer.data.models

import com.google.gson.annotations.SerializedName

data class SearchActorResult(
    val page : Int,
    val results : List<MediaPerson>,
    @SerializedName("total_pages")
    val totalPages : Int,
    @SerializedName("total_results")
    val totalResults : Int
)