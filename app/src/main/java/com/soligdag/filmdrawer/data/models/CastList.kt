package com.soligdag.filmdrawer.data.models

import com.google.gson.annotations.SerializedName


data class CastList(
    @SerializedName("id")
    val id : Int,
    @SerializedName("cast")
    val castMembers: List<CastMember>)