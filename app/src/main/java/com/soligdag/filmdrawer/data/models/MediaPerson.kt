package com.soligdag.filmdrawer.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MediaPerson(
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String,
    )

//@SerializedName("adult"                ) var adult              : Boolean?            = null,
//@SerializedName("gender"               ) var gender             : Int?                = null,
//@SerializedName("id"                   ) var id                 : Int?                = null,
//@SerializedName("known_for_department" ) var knownForDepartment : String?             = null,
//@SerializedName("name"                 ) var name               : String?             = null,
//@SerializedName("original_name"        ) var originalName       : String?             = null,
//@SerializedName("popularity"           ) var popularity         : Double?             = null,
//@SerializedName("profile_path"         ) var profilePath        : String?             = null,
//@SerializedName("known_for"            ) var knownFor           : ArrayList<KnownFor> = arrayListOf()