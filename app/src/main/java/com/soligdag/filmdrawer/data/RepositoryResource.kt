package com.soligdag.filmdrawer.data

import org.json.JSONObject

sealed class RepositoryResource <out T> {
    data class Success<out T>(val value: T): RepositoryResource<T>()
    data class Error(val error: ResourceError): RepositoryResource<Nothing>()
}

data class ResourceError (val errorType : String = "", val errorBody : JSONObject? = null, val errorMessage : String = "")