package com.soligdag.filmdrawer.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soligdag.filmdrawer.data.models.Recommendation

@Dao
interface RecommendationsDao {
    @Query("SELECT * FROM Recommendations")
    fun getAll() : List<Recommendation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg item: Recommendation)

    @Delete
    fun delete(item : Recommendation)

}