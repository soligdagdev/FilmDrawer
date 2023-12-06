package com.soligdag.filmdrawer.data.models

import com.google.gson.annotations.SerializedName


data class ProductionCountries (

  @SerializedName("iso_3166_1" ) var iso31661 : String? = null,
  @SerializedName("name"       ) var name     : String? = null

)