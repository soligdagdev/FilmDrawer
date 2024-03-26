package com.soligdag.filmdrawer.data.repositories

interface SharedPrefRepository {
    fun clearAllSharedPrefs()
    fun setWishListVersionPref(num : Int)
    fun getWishListVersionPref() : Int
}

const val FILMDRAWER_PREFS = "FilmDrawerPrefs"
const val WISHLIST_VERSION_PREF_KEY = "WishListVersion"