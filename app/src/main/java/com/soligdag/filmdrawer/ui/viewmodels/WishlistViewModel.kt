package com.soligdag.filmdrawer.ui.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soligdag.filmdrawer.data.RepositoryResource
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.data.repositories.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WishlistViewModel @Inject constructor(private val userDataRepository : UserDataRepository) : ViewModel() {
    private var _uiState = MutableStateFlow(WishlistScreenState())
    val uiState = _uiState.asStateFlow()
    init {
        getAllWishlistItems()
        checkForLatestData()
    }

    private fun getAllWishlistItems() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch { //this: CoroutineScope
            userDataRepository.getWishListFlow().flowOn(Dispatchers.IO).collect { wishListResult: List<WishlistItem> ->
                _uiState.update { it.copy(isLoading = false, wishlistItems = wishListResult) }
            }
        }
    }

    private fun checkForLatestData() {
        viewModelScope.launch {
            userDataRepository.checkForLatestWishListData()
        }
    }

    fun removeFromWishlist(wishlistItem: WishlistItem) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            when(val result =  userDataRepository.deleteWishlistItem(wishlistItem = wishlistItem)) {
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