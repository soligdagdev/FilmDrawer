package com.soligdag.filmdrawer.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.ui.navigation.Destination


@Dao
interface WishlistDao {
    @Query("SELECT * FROM WishList")
    fun getAll() : List<WishlistItem>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg item: WishlistItem)

    @Delete
    fun delete(item : WishlistItem)

}