package com.soligdag.filmdrawer.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soligdag.filmdrawer.FilmDrawerApplication
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.data.models.dummyWishlist
import com.soligdag.filmdrawer.ui.components.LoadingDialog
import com.soligdag.filmdrawer.ui.components.WatchListMediaItemComposable
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import com.soligdag.filmdrawer.ui.viewmodels.WishlistViewModel
import com.soligdag.filmdrawer.ui.viewmodels.viewModelFactory


@Composable
fun WishlistScreen(onItemClicked: (wishlistItem: WishlistItem) -> Unit,
    viewModel: WishlistViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = viewModelFactory {
        WishlistViewModel(FilmDrawerApplication.container.mediaRepository)
    })
) {
    Log.d("Wishlist Screen", "Root Composing")
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        LoadingDialog()
    }
    if (uiState.wishlistItems != null) {
        Log.d("Wishlist Screen", "Data received in state")
            WatchlistContent(
                uiState.wishlistItems!!,
                onItemClicked = { onItemClicked(it) },
                onItemRemoveClicked = {viewModel.removeFromWishlist(it)},
                onShareAsRecommendationClicked = {})
    }

}



@Composable
private fun WatchlistContent(
    watchlist: List<WishlistItem>,
    onItemClicked: (wishlistItem: WishlistItem) -> Unit,
    onItemRemoveClicked: (wishlistItem: WishlistItem) -> Unit,
    onShareAsRecommendationClicked: (wishlistItem: WishlistItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 42.dp)
    ) {
        Text(
            text = "My Watchlist",
            style = Typography.titleMedium.copy(fontSize = 21.sp),
            modifier = Modifier.padding(16.dp)
        )
        if(watchlist.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(watchlist) { _, item ->
                    WatchListMediaItemComposable(
                        modifier = Modifier,
                        wishlistItem = item,
                        onItemClicked = { onItemClicked(item) },
                        onRemoveFromListClicked = { onItemRemoveClicked(item) },
                        onSendAsRecommendationClicked = { onShareAsRecommendationClicked(item) })
                }
            }
        }
        else {
           Column(Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, bottom = 80.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(modifier = Modifier.padding(16.dp), text = "Your list is empty", style = Typography.headlineSmall)
                Text(text = "You can add movies or TV series here by searching or from received recommendations.", style = Typography.bodyMedium.copy(textAlign = TextAlign.Center))
           }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun WatchlistPreview() {
    FilmDrawerTheme {
        WatchlistContent(
            listOf(),
            onItemClicked = {},
            onItemRemoveClicked = {},
            onShareAsRecommendationClicked = {})
    }
}