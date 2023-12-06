package com.soligdag.filmdrawer.ui.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.CastList
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.MovieDetail
import com.soligdag.filmdrawer.data.models.SearchActorResult
import com.soligdag.filmdrawer.data.models.SearchMediaResult
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieId: Int, private val mediaRepository: MediaRepository) :
    ViewModel() {
    private var _uiState = MutableStateFlow(MovieDetailScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getMovieDetail()
    }


    private fun getMovieDetail() {
        _uiState.update { MovieDetailScreenState(isLoading = true) }
        viewModelScope.launch {
            when (val moviesResult = mediaRepository.getMovieDetail(movieId = movieId)) {
                is RepositoryResource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            movieDetail = moviesResult.value.first,
                            castList = moviesResult.value.second
                        )
                    }
                }

                is RepositoryResource.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun addMovieToWishList(movie: MovieDetail) {
        val mediaItem = MediaItem(movie)
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = mediaRepository.addItemToWishlist(mediaItem)) {
                is RepositoryResource.Success -> {
                    _uiState.update { it.copy(isLoading = false, addedToWishlist = true) }
                }
                is RepositoryResource.Error -> {}
            }
        }
    }


    fun resetState() {
        _uiState.update { it.copy(isLoading = false, addedToWishlist = true) }
    }

    data class MovieDetailScreenState(
        val isLoading: Boolean = false,
        val movieDetail: MovieDetail? = null,
        val castList: CastList? = null,
        val addedToWishlist : Boolean = false
    )
}