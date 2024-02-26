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
import com.soligdag.filmdrawer.data.models.TrendingMovie
import com.soligdag.filmdrawer.data.models.TrendingSeries
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.data.models.dummyMovie
import com.soligdag.filmdrawer.data.network.NetworkAPIService
import com.soligdag.filmdrawer.data.room.FilmDrawerDatabase
import com.soligdag.filmdrawer.ui.navigation.Destination
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor( private val networkAPIService : NetworkAPIService, private val database: FilmDrawerDatabase) : MediaRepository {
    private val tmdbAPIToken  = BuildConfig.TMDB_API_KEY


    private fun timeDiffInDays(savedTime : Long) : Long {
        val diff = System.currentTimeMillis()-savedTime
        return TimeUnit.MILLISECONDS.toDays(diff)
       // return 2
    }
    override suspend fun getTrendingMovies(): RepositoryResource<List<MediaItem>> {
        return try {
            var dbResult = database.trendingDataDao().getAllTrendingMovies()
            if(dbResult.isNullOrEmpty() || timeDiffInDays(dbResult[0].createdAt)>1) { // Data is either empty or outdated
                val result = networkAPIService.getPopularMovies(page = 1, token = tmdbAPIToken)
                if(dbResult?.size?:0>0)
                    database.trendingDataDao().deleteAllTrendingMovies()
                for(mediaItem in result.results) {
                    val trendingMovie = TrendingMovie( mediaItem = mediaItem.copy(name = "", mediaType = "movie"), createdAt = System.currentTimeMillis())
                    database.trendingDataDao().insertAllTrendingMovies(trendingMovie)
                }
                dbResult = database.trendingDataDao().getAllTrendingMovies()
            }
            val result = mutableListOf<MediaItem>()
            for(trendingMovie in dbResult!!) {
                result.add(trendingMovie.mediaItem)
            }
            RepositoryResource.Success(result)
        }
        catch (ex : Exception) {
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString()))
        }
    }

    override suspend fun getTrendingTVShows(): RepositoryResource<List<MediaItem>> {
        return try {
            var dbResult = database.trendingDataDao().getAllTrendingSeries()
            if(dbResult.isNullOrEmpty() || timeDiffInDays(dbResult[0].createdAt)>1) { // Data is either empty or outdated
                val result = networkAPIService.getPopularTVSeries(page = 1, token = tmdbAPIToken)
                if(dbResult?.size?:0>0)
                    database.trendingDataDao().deleteAllTrendingSeries()
                for(mediaItem in result.results) {
                    val trendingSeries = TrendingSeries( mediaItem = mediaItem.copy(title = "", mediaType = "series", releaseDate = ""), createdAt = System.currentTimeMillis())
                    database.trendingDataDao().insertAllTrendingSeries(trendingSeries)
                }
                dbResult = database.trendingDataDao().getAllTrendingSeries()
            }
            val result = mutableListOf<MediaItem>()
            for(trendingSeries in dbResult!!) {
                result.add(trendingSeries.mediaItem)
            }
            RepositoryResource.Success(result)
        }
        catch (ex : Exception) {
            RepositoryResource.Error(ResourceError(errorMessage = ex.message.toString()))
        }
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




}