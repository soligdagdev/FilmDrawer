package com.soligdag.filmdrawer.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.models.SearchActorResult
import com.soligdag.filmdrawer.data.models.SearchMediaResult
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import com.soligdag.filmdrawer.data.repositories.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//private var _uiState= MutableStateFlow(SearchUIState())
//var uiState = _uiState.asStateFlow()
@HiltViewModel
class HomeViewModel @Inject constructor(val mediaRepository : MediaRepository, val userDataRepository: UserDataRepository)  : ViewModel() {

//    @HiltViewModel
//    class WishlistViewModel @Inject constructor(private val userDataRepository : UserDataRepositoryImpl) : ViewModel() {
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery = _searchQuery
    private var _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()
    init {
        Log.d("HOME", "Getting Home Data")
        getRecentRecommendations()
        getTrendingMovies()
        getTrendingTVShows()
    }



    fun onSearchQueryChange(newText : String) {
        _searchQuery.value = newText
    }

    private fun getRecentRecommendations() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when(val response = userDataRepository.getRecentRecommendations()) {
                is RepositoryResource.Success -> {
                    _uiState.update { it.copy(isLoading = false,  recommendations = response.value) }
                }
                is RepositoryResource.Error -> {
                    _uiState.update { it.copy(isLoading = false, recommendations = null)}
                }
            }
        }
    }

    private fun getTrendingMovies() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when(val response = mediaRepository.getTrendingMovies()) {
                is RepositoryResource.Success -> {
                    _uiState.update { it.copy(isLoading = false, trendingMovies = response.value) }
                }
                is RepositoryResource.Error -> {
                    _uiState.update { it.copy(isLoading = false, trendingMovies = null)}
                }
            }
        }
    }
    private fun getTrendingTVShows() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when(val response = mediaRepository.getTrendingTVShows()) {
                is RepositoryResource.Success -> {
                    _uiState.update { it.copy(isLoading = false, trendingSeries = response.value) }
                }
                is RepositoryResource.Error -> {
                    _uiState.update { it.copy(isLoading = false, trendingSeries = null)}
                }
            }
        }
    }

}

data class HomeUIState(
    val isLoading: Boolean = false,
    val recommendations : List<Recommendation>? = null,
    val trendingMovies : List<MediaItem>? = null,
    val trendingSeries : List<MediaItem>? = null,
    val mediaPersonResult: SearchActorResult? = null
)

