package com.soligdag.filmdrawer.data.repositories

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject

class SharedPrefRepositoryImpl@Inject constructor(
    private val context: Context
) : SharedPrefRepository  {
    private val sharedPrefsInstance = context.getSharedPreferences(FILMDRAWER_PREFS,Context.MODE_PRIVATE)
    override fun clearAllSharedPrefs() {
        sharedPrefsInstance.edit().remove(WISHLIST_VERSION_PREF_KEY).apply()
    }

    override fun setWishListVersionPref(num: Int) {
        sharedPrefsInstance.edit {
            putInt(WISHLIST_VERSION_PREF_KEY, num).commit()
        }
    }

    override fun getWishListVersionPref(): Int {
        return  sharedPrefsInstance.getInt(WISHLIST_VERSION_PREF_KEY,0)
    }

}