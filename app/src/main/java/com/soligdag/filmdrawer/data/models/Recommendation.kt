package com.soligdag.filmdrawer.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Recommendations")
data class Recommendation(
    val isReceivedRecommendation: Boolean = false,
    val recommendationId : Int,
    @PrimaryKey val recommenderId : Int,
    val recommenderName : String,
    val receiverId : Int,
    val receiverName : String,
    @Embedded
    val mediaItem : MediaItem,
    val date : String,
    val recommendationText : String
    )