package com.soligdag.filmdrawer.ui.viewmodels

import android.media.browse.MediaBrowser.MediaItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import com.soligdag.filmdrawer.data.repositories.MediaRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val mediaRepository : MediaRepositoryImpl)  : ViewModel() {
    init {
        getTrendingMovies()
    }
    private var trendingMovies = MutableLiveData<MediaItem>()
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery = _searchQuery


    fun onSearchQueryChange(newText : String) {
        _searchQuery.value = newText
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            mediaRepository.getTrendingMovies()
        }
    }

}

