package com.soligdag.filmdrawer.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.CastList
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.SeriesDetail
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.data.repositories.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishlistViewModel(private val mediaRepository: MediaRepository) : ViewModel() {
    private var _uiState = MutableStateFlow(WishlistScreenState())
    val uiState = _uiState.asStateFlow()
    init {
        Log.d("Wishlist Screen", "ViewModel initializing")
        getAllWishlistItems()
    }

    private fun getAllWishlistItems() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            when(val result =  mediaRepository.getAllWishlistItems()) {
                is RepositoryResource.Success -> {
                    _uiState.update { it.copy(isLoading = false, wishlistItems = result.value ) }
                }
                is RepositoryResource.Error -> {
                    _uiState.update { it.copy(isLoading = false, wishlistItems = null ) }
                }
            }
        }
    }

    fun removeFromWishlist(wishlistItem: WishlistItem) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            when(val result =  mediaRepository.deleteWishlistItem(wishlistItem = wishlistItem)) {
                is RepositoryResource.Success -> {
                    _uiState.update { it.copy(isLoading = false, wishlistItems = result.value) }
                }
                is RepositoryResource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    data class WishlistScreenState(
        val isLoading: Boolean = false,
        var wishlistItems: List<WishlistItem>? = null,
    )
}