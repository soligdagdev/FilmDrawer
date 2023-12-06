package com.soligdag.filmdrawer.data.models

data class Recommendation(
    val isReceivedRecommendation: Boolean = false,
    val id : Int,
    val recommenderId : Int,
    val recommenderName : String,
    val receiverId : Int,
    val receiverName : String,
    val mediaItem : MediaItem,
    val date : String,
    val recommendationText : String
    )