package com.soligdag.filmdrawer.data.repositories
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.CastList
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.MovieDetail
import com.soligdag.filmdrawer.data.models.SearchActorResult
import com.soligdag.filmdrawer.data.models.SearchMediaResult
import com.soligdag.filmdrawer.data.models.SeriesDetail
import com.soligdag.filmdrawer.data.models.WishlistItem

interface MediaRepository {
    suspend fun getTrendingMovies() : RepositoryResource<List<MediaItem>>
    suspend fun getTrendingTVShows() : RepositoryResource<List<MediaItem>>
    suspend fun getSearchResultsForMovies(searchQuery : String, pageNumber : Int) : RepositoryResource<SearchMediaResult>
    suspend fun getSearchResultsForSeries(searchQuery : String, pageNumber : Int) : RepositoryResource<SearchMediaResult>
    suspend fun getSearchResultsForMediaPerson(searchQuery : String, pageNumber : Int) : RepositoryResource<SearchActorResult>
    suspend fun getMovieDetail(movieId : Int) : RepositoryResource<Pair<MovieDetail, CastList>>
    suspend fun getSeriesDetail(seriesId : Int) : RepositoryResource<Pair<SeriesDetail, CastList>>


//    suspend fun getTrendingMoviesFromDB() : RepositoryResource<List<MediaItem>>
//    suspend fun getTrendingSeriesFromDB() : RepositoryResource<List<MediaItem>>


}