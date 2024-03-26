package com.soligdag.filmdrawer.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.ui.navigation.Destination
import kotlinx.coroutines.flow.Flow


@Dao
interface WishlistDao {
    @Query("SELECT * FROM WishList")
    fun getAll() : List<WishlistItem>

    @Query("SELECT * FROM WishList")
    fun getAllFlow() : Flow<List<WishlistItem>>
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg item: WishlistItem)

    @Insert
    fun insertAllItems(items: List<WishlistItem>)
    @Delete
    fun delete(item : WishlistItem)

    @Query("DELETE FROM WishList")
    fun deleteAll()

}