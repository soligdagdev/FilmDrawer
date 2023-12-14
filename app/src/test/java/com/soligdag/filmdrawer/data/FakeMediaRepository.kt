package com.soligdag.filmdrawer.data

import com.soligdag.filmdrawer.data.models.CastList
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.MovieDetail
import com.soligdag.filmdrawer.data.models.SearchActorResult
import com.soligdag.filmdrawer.data.models.SearchMediaResult
import com.soligdag.filmdrawer.data.models.SeriesDetail
import com.soligdag.filmdrawer.data.repositories.MediaRepository

class FakeMediaRepository : MediaRepository {
    override suspend fun getTrendingMovies(): RepositoryResource<List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrendingTVShows(): RepositoryResource<List<MediaItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchResultsForMovies(
        searchQuery: String,
        pageNumber: Int
    ): RepositoryResource<SearchMediaResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchResultsForSeries(
        searchQuery: String,
        pageNumber: Int
    ): RepositoryResource<SearchMediaResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchResultsForMediaPerson(
        searchQuery: String,
        pageNumber: Int
    ): RepositoryResource<SearchActorResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(movieId: Int): RepositoryResource<Pair<MovieDetail, CastList>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeriesDetail(seriesId: Int): RepositoryResource<Pair<SeriesDetail, CastList>> {
        TODO("Not yet implemented")
    }
}