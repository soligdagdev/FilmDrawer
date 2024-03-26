package com.soligdag.filmdrawer.data.models

data class Friend(
    var id : String,
    var name : String,
    var isSelected : Boolean = false,
    var photoPath : String = ""
)
