package com.soligdag.filmdrawer.data.network

import com.soligdag.filmdrawer.data.models.CastList
import com.soligdag.filmdrawer.data.models.MovieDetail
import com.soligdag.filmdrawer.data.models.SearchActorResult
import com.soligdag.filmdrawer.data.models.SearchMediaResult
import com.soligdag.filmdrawer.data.models.SeriesDetail
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkAPIService {
    // private  val token  = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MWQxNzU4ZjhhZGE0ZGFmOGVlYjUxMzMwNjRkODJmYSIsInN1YiI6IjY1MjZmNGUzODEzODMxMDBjNDg5YTY5MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.o8ps6XFTtbxoCw1zepMLynDDPJmHckFyf48EZxpdIFs"
    @GET("volumes")
    suspend fun getPopularMovies(
        @Query("q") queryString: String,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int
    ): Response<JSONObject>

    @GET("volumes/{volumeId}")
    suspend fun getPopularTVShows(@Path("volumeId") volumeId: String): Response<JSONObject>


    @GET("search/movie")
    suspend fun getSearchResultForMovies(
        @Query("query") queryString: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json"
    ): SearchMediaResult

    @GET("search/tv")
    suspend fun getSearchResultForSeries(
        @Query("query") queryString: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json"
    ): SearchMediaResult

    @GET("search/person")
    suspend fun getSearchResultForMediaPerson(
        @Query("query") queryString: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json"
    ): SearchActorResult

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int, @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json"
    ): MovieDetail


    @GET("movie/{movieId}/credits")
    suspend fun getMovieCast(
        @Path("movieId") movieId: Int, @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json"
    ): CastList



    @GET("tv/{seriesId}")
    suspend fun getSeriesDetail(
        @Path("seriesId") seriesId: Int, @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json"
    ): SeriesDetail

    @GET("tv/{seriesId}/credits")
    suspend fun getSeriesCast(
        @Path("seriesId") seriesId: Int, @Header("Authorization") token: String,
        @Header("accept") accept: String = "application/json"
    ): CastList
}