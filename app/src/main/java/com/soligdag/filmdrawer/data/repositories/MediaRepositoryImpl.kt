package com.soligdag.filmdrawer.data.repositories

import android.util.Log
import com.soligdag.filmdrawer.BuildConfig
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.ResourceError
import com.soligdag.filmdrawer.data.models.CastList
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.MovieDetail
import com.soligdag.filmdrawer.data.models.SearchActorResult
import com.soligdag.filmdrawer.data.models.SearchMediaResult
import com.soligdag.filmdrawer.data.models.SeriesDetail
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.data.models.dummyMovie
import com.soligdag.filmdrawer.data.network.NetworkAPIService
import com.soligdag.filmdrawer.data.room.FilmDrawerDatabase
import com.soligdag.filmdrawer.ui.navigation.Destination
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MediaRepositoryImpl(private val networkAPIService : NetworkAPIService, private val database : FilmDrawerDatabase) : MediaRepository {


    private val tmdbAPIToken  = BuildConfig.TMDB_API_KEY


    override suspend fun getTrendingMovies(): RepositoryResource<List<MediaItem>> {
        return RepositoryResource.Success(listOf(dummyMovie))
    }

    override suspend fun getTrendingTVShows(): RepositoryResource<List<MediaItem>> {
        return RepositoryResource.Success(listOf(dummyMovie))
    }

    override suspend fun getSearchResultsForMovies(
        searchQuery: String,
        pageNumber: Int
    ): RepositoryResource<SearchMediaResult> {
        return try {
            val result = networkAPIService.getSearchResultForMovies(queryString = searchQuery, page = pageNumber, token = tmdbAPIToken)
            RepositoryResource.Success(result)
        } catch (ex : Exception) {
            Log.d("", ex.message.toString())
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString()))
        }
    }

    override suspend fun getSearchResultsForSeries(
        searchQuery: String,
        pageNumber: Int
    ): RepositoryResource<SearchMediaResult> {
        return try {
            Log.d("Search Media", "Getting Series")
            val result = networkAPIService.getSearchResultForSeries(queryString = searchQuery, page = pageNumber, token = tmdbAPIToken)
            RepositoryResource.Success(result)
        } catch (ex : Exception) {
            Log.d("", ex.message.toString())
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString()))
        }
    }

    override suspend fun getSearchResultsForMediaPerson(
        searchQuery: String,
        pageNumber: Int
    ): RepositoryResource<SearchActorResult> {
        return try {
            Log.d("Search Media", "Getting Actors")
            val result = networkAPIService.getSearchResultForMediaPerson(queryString = searchQuery, page = pageNumber, token = tmdbAPIToken)
            RepositoryResource.Success(result)
        } catch (ex : Exception) {
            Log.d("", ex.message.toString())
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString()))
        }
    }

    override suspend fun getMovieDetail(movieId: Int): RepositoryResource<Pair<MovieDetail,CastList>> {
        return try {
            delay(1000)
            val movieDetail = networkAPIService.getMovieDetail(movieId, token = tmdbAPIToken)
            val castDetail = networkAPIService.getMovieCast(movieId, token = tmdbAPIToken)
            RepositoryResource.Success(Pair(movieDetail,castDetail))
        }
        catch (ex : Exception) {
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString()))
        }
    }

    override suspend fun getSeriesDetail(seriesId: Int): RepositoryResource<Pair<SeriesDetail,CastList>> {
        return try {
            delay(1000)
            val seriesDetail = networkAPIService.getSeriesDetail(seriesId, token = tmdbAPIToken)
            val castDetail = networkAPIService.getSeriesCast(seriesId, token = tmdbAPIToken)
            RepositoryResource.Success(Pair(seriesDetail,castDetail))
        }
        catch (ex : Exception) {
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString()))
        }
    }

    override suspend fun addItemToWishlist(mediaItem: MediaItem): RepositoryResource<MediaItem> {
        val wishlistItem = WishlistItem(mediaItem = mediaItem)
        database.wishlistDao().insertAll(wishlistItem)
        return RepositoryResource.Success(mediaItem)
    }

    override suspend fun getAllWishlistItems(): RepositoryResource<List<WishlistItem>> {
        val allWishlistItems = database.wishlistDao().getAll()
        return RepositoryResource.Success(allWishlistItems)
    }

    override suspend fun deleteWishlistItem(wishlistItem: WishlistItem): RepositoryResource<List<WishlistItem>> {
        database.wishlistDao().delete(wishlistItem)
        val allWishlistItems = database.wishlistDao().getAll()
        return RepositoryResource.Success(allWishlistItems)

    }


}