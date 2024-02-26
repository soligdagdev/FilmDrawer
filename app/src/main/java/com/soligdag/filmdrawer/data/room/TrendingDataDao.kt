package com.soligdag.filmdrawer.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.models.TrendingMovie
import com.soligdag.filmdrawer.data.models.TrendingSeries

@Dao
interface TrendingDataDao {

    @Query("SELECT * FROM TrendingMovies")
    suspend fun getAllTrendingMovies() : List<TrendingMovie>?

    @Query("SELECT * FROM TrendingSeries")
    suspend fun getAllTrendingSeries() : List<TrendingSeries>?

    @Query("DELETE FROM TrendingMovies")
    suspend fun deleteAllTrendingMovies()

    @Query("DELETE FROM TrendingSeries")
    suspend fun deleteAllTrendingSeries()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTrendingMovies(vararg item: TrendingMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTrendingSeries(vararg item: TrendingSeries)

//    @Delete
//    fun deleteTrendingMovie(item : Recommendation)

}