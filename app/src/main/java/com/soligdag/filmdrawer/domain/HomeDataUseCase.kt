package com.soligdag.filmdrawer.domain

import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import com.soligdag.filmdrawer.data.repositories.UserDataRepository
import com.soligdag.filmdrawer.ui.navigation.Destination


/*
* HomeDataUseCase gets data to be loaded for home screen. It includes
* Recent recommendations : Always fetched from firestore data store (Can be optimized later)
* Trending Movies : Check if local DB has records from today's date then get from local DB, if not then get from web API
* Trending series : Check if local DB has records from today's date the get from local DB, if not then get from web API
* */

class HomeDataUseCase(val userDataRepository: UserDataRepository, val mediaRepository: MediaRepository) {
    suspend fun getData() {
        val recommendations : List<Recommendation>? = null
        val recentMovies : List<MediaItem>? = null
        val recentSeries : List<MediaItem>? = null
        when(val response = mediaRepository.getTrendingMovies()) {
            is RepositoryResource.Error -> {

            }
            is RepositoryResource.Success -> {

            }
        }
    }
}

data class HomeUseCaseResponse(val recommendations : List<Recommendation>?, val trendingMovies : List<MediaItem>?, val trendingSeries : List<MediaItem>?)