package com.soligdag.filmdrawer.ui.screens


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.soligdag.filmdrawer.FilmDrawerApplication
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.MediaPerson
import com.soligdag.filmdrawer.data.models.SearchActorResult
import com.soligdag.filmdrawer.data.models.SearchMediaResult
import com.soligdag.filmdrawer.data.models.dummySearchMediaPersonResponse
import com.soligdag.filmdrawer.data.models.dummySearchMoviesResponse
import com.soligdag.filmdrawer.data.models.dummySearchSeriesResponse
import com.soligdag.filmdrawer.ui.components.CustomSearchView
import com.soligdag.filmdrawer.ui.components.LoadingDialog
import com.soligdag.filmdrawer.ui.components.MediaPersonComposable
import com.soligdag.filmdrawer.ui.components.MovieItemComposable
import com.soligdag.filmdrawer.ui.components.SeriesItemComposable
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import com.soligdag.filmdrawer.ui.viewmodels.SearchViewModel
import com.soligdag.filmdrawer.ui.viewmodels.viewModelFactory

/** Screen Definition And Purpose
 *
 */
@Composable
fun SearchScreen(
    queryText: String = "", viewModel: SearchViewModel = hiltViewModel(), onMediaItemClicked: (mediaItem: MediaItem) -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsState()

    if(uiState.isLoading) {
        LoadingDialog()
    }
    val searchQuery by viewModel.searchQuery.observeAsState()
    SearchScreenContent(
        searchQueryText = searchQuery ?: "",
        searchMoviesResult = uiState.moviesResult,
        searchSeriesResult = uiState.seriesResult,
        onQueryTextChanged = { viewModel.updateQueryText(it)},
        searchMediaPersonResult = uiState.mediaPersonResult,
        onSearchBtnClicked = { viewModel.getSearchResults() },
        onMediaItemClicked = { onMediaItemClicked(it) }
        )
}

@Composable
fun SearchScreenContent(
    searchQueryText: String = "",
    searchMoviesResult: SearchMediaResult?,
    searchSeriesResult: SearchMediaResult?,
    searchMediaPersonResult: SearchActorResult?,
    onQueryTextChanged: (newText: String) -> Unit = {},
    onSearchBtnClicked: (queryText: String) -> Unit = {},
    onMediaItemClicked: (mediaItem: MediaItem) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        CustomSearchView(
            modifier = Modifier.padding(top = 32.dp),
            queryText = searchQueryText,
            onQueryTextChange = { onQueryTextChanged(it) },
            onQueryTextSubmit = { onSearchBtnClicked(it) })

        /**
         *  Search Results
         * */

        if(searchMoviesResult!=null) {
            SearchCategoryComposable(
                category = "Movies",
                totalResults = searchMoviesResult.totalResults,
                results = searchMoviesResult.results,
                onItemClicked = { onMediaItemClicked(searchMoviesResult.results[it].apply { mediaType = "movie" }) })
        }

        if(searchSeriesResult!=null) {
            SearchCategoryComposable(
                category = "Series",
                totalResults = searchSeriesResult.totalResults,
                results = searchSeriesResult.results,
                onItemClicked = { onMediaItemClicked(searchSeriesResult.results[it].apply { mediaType = "series" }) })
        }

        if(searchMediaPersonResult!=null) {
            SearchCategoryComposable(
                category = "People",
                totalResults = searchMediaPersonResult.totalResults,
                results = searchMediaPersonResult.results,
                onItemClicked = { })
        }



    }
}


@Composable
private fun SearchCategoryComposable(
    category: String,
    totalResults: Int,
    results: List<Any>,
    onItemClicked: (index: Int) -> Unit
) {

    var showDetails by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(modifier = Modifier.clickable { showDetails = !showDetails }) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = category, style = Typography.titleMedium.copy(fontSize = 21.sp))
                Text(
                    text = totalResults.toString(),
                    style = Typography.titleMedium.copy(fontSize = 18.sp),
                    modifier = Modifier.padding(start = 32.dp)
                )
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    modifier = Modifier.padding(start = 32.dp)
                )
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Green)
                ) // height and background only for demonstration

                Text(
                    text = "More",
                    style = Typography.titleMedium.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    ), modifier = Modifier.clickable {  }
                )
            }
        }

        AnimatedVisibility(visible = showDetails) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 0.dp, start = 8.dp, end = 8.dp)
            ) {
                if(results.isNotEmpty() && results[0] is MediaItem) {
                    itemsIndexed(results as List<MediaItem>) { index, item ->
                        if(category == "Movies") {
                            MovieItemComposable(item, onMediaItemClicked = {
                                Log.d("", item.title)
                                onItemClicked(index)
                            })
                        }
                        else {
                            SeriesItemComposable(item, onMediaItemClicked = {
                                onItemClicked(index)
                            })
                        }

                    }
                }
                else if(results.isNotEmpty() && results[0] is MediaPerson){
                    itemsIndexed(results as List<MediaPerson>) { index, item ->
                        MediaPersonComposable(item, onMediaPersonClicked = {
                            onItemClicked(index)
                        })
                    }
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    FilmDrawerTheme() {
        SearchScreenContent(
            "", searchMoviesResult = dummySearchMoviesResponse,
            searchSeriesResult = dummySearchSeriesResponse,
            searchMediaPersonResult = dummySearchMediaPersonResponse
        )
    }

}