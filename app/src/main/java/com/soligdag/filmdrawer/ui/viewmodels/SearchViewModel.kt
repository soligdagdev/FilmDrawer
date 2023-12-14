package com.soligdag.filmdrawer.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.SearchActorResult
import com.soligdag.filmdrawer.data.models.SearchMediaResult
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mediaRepository: MediaRepository) : ViewModel() {
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery = _searchQuery
    private var _uiState= MutableStateFlow(SearchUIState())
    var uiState = _uiState.asStateFlow()
    init {
        val startSearchQuery = savedStateHandle.get<String>("searchText")?:"Twilight"
        if(startSearchQuery!="") {
            _searchQuery.value = startSearchQuery
            getSearchResults()
        }
    }

    fun getSearchResults() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when(val moviesResult = mediaRepository.getSearchResultsForMovies(_searchQuery.value?:"", pageNumber = 1)) {
                is RepositoryResource.Success -> { _uiState.update { it.copy(isLoading = true, moviesResult = moviesResult.value) }}
                is RepositoryResource.Error -> { _uiState.update { it.copy(isLoading = false) } }
            }
            when(val seriesResult = mediaRepository.getSearchResultsForSeries(_searchQuery.value?:"", pageNumber = 1)) {
                is RepositoryResource.Success -> { _uiState.update { it.copy(isLoading = true, seriesResult = seriesResult.value) }}
                is RepositoryResource.Error -> { _uiState.update { it.copy(isLoading = false) } }
            }
            when(val mediaPersonResult = mediaRepository.getSearchResultsForMediaPerson(_searchQuery.value?:"", pageNumber = 1)) {
                is RepositoryResource.Success -> { _uiState.update { it.copy(isLoading = false, mediaPersonResult = mediaPersonResult.value) }}
                is RepositoryResource.Error -> { _uiState.update { it.copy(isLoading = false) } }
            }
        }
    }

    fun updateQueryText(newText : String) {
        _searchQuery.value = newText
    }


}

data class SearchUIState(
    val isLoading: Boolean = false,
    val moviesResult : SearchMediaResult? = null,
    val seriesResult : SearchMediaResult? = null,
    val mediaPersonResult: SearchActorResult? = null
)