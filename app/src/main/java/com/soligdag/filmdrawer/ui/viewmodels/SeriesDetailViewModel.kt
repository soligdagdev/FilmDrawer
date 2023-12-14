package com.soligdag.filmdrawer.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.CastList
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.SeriesDetail
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import com.soligdag.filmdrawer.data.repositories.UserDataRepositoryImpl
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//class SeriesDetailViewModel @Inject constructor(@Assisted private val savedStateHandle: SavedStateHandle, private val mediaRepository: MediaRepository) : ViewModel() {
class SeriesDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val mediaRepository: MediaRepository, private val userDataRepository : UserDataRepositoryImpl) : ViewModel() {

    private var _uiState = MutableStateFlow(SeriesDetailScreenState())
    val uiState = _uiState.asStateFlow()
    var seriesId : Int = 0
    init {
        seriesId = savedStateHandle.get<Int>("id")?:549
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
            userDataRepository.addItemToWishlist(mediaItem)
        }
    }
    data class SeriesDetailScreenState(
        val isLoading: Boolean = false,
        val seriesDetail: SeriesDetail? = null,
        val castList : CastList? = null
    )
}