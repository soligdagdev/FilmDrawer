package com.soligdag.filmdrawer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.models.TrendingMovie
import com.soligdag.filmdrawer.data.models.TrendingSeries
import com.soligdag.filmdrawer.data.models.WishlistItem


@Database(entities = [WishlistItem::class, Recommendation::class, TrendingMovie::class, TrendingSeries::class], version = 1)
abstract class FilmDrawerDatabase : RoomDatabase() {
    abstract fun wishlistDao() : WishlistDao
    abstract fun recommendationsDao() : RecommendationsDao
    abstract fun trendingDataDao() : TrendingDataDao

}