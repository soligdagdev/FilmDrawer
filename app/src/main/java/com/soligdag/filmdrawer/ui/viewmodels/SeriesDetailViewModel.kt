package com.soligdag.filmdrawer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.CastList
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.SeriesDetail
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SeriesDetailViewModel(private val seriesId : Int,  private val mediaRepository: MediaRepository) : ViewModel() {

    private var _uiState = MutableStateFlow(SeriesDetailScreenState())
    val uiState = _uiState.asStateFlow()
    init {
        getSeriesDetail()
    }



    private fun getSeriesDetail() {
        _uiState.update { SeriesDetailScreenState(isLoading = true) }
        viewModelScope.launch {
            when(val seriesDetailResult = mediaRepository.getSeriesDetail(seriesId = seriesId)) {
                is RepositoryResource.Success -> {
                    _uiState.update { it.copy(isLoading = false, seriesDetail = seriesDetailResult.value.first, castList = seriesDetailResult.value.second)
                    }}
                is RepositoryResource.Error -> {
                    _uiState.update { it.copy(isLoading = false)
                    } }
            }
        }
    }

    fun addSeriesToWishlist(seriesDetail: SeriesDetail) {
        val mediaItem = MediaItem(seriesDetail)
        viewModelScope.launch(Dispatchers.IO) {
            mediaRepository.addItemToWishlist(mediaItem)
        }
    }
    data class SeriesDetailScreenState(
        val isLoading: Boolean = false,
        val seriesDetail: SeriesDetail? = null,
        val castList : CastList? = null
    )
}