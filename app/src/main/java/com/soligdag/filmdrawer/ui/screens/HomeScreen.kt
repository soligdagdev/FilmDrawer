package com.soligdag.filmdrawer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Recommend
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.models.dummyListOfMovie
import com.soligdag.filmdrawer.data.models.dummyListOfSeries
import com.soligdag.filmdrawer.ui.components.CustomSearchView
import com.soligdag.filmdrawer.ui.components.LoadingDialog
import com.soligdag.filmdrawer.ui.components.MovieItemComposable
import com.soligdag.filmdrawer.ui.components.RecentRecommendationItemComposable
import com.soligdag.filmdrawer.ui.components.SeriesItemComposable
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import com.soligdag.filmdrawer.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onMediaItemClicked: (mediaItem: MediaItem) -> Unit = { },
    onSearchTextSubmitted: (queryText: String) -> Unit = { }
) {
    val searchQuery by viewModel.searchQuery.observeAsState()
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        LoadingDialog()
    }

    HomeScreenContent(
        searchQueryText = searchQuery,
        onQueryTextChange = {
            viewModel.onSearchQueryChange(newText = it)
        },
        onSearchTextSubmitted = { onSearchTextSubmitted(it) },
        trendingMovies = uiState.trendingMovies ?: listOf(),
        trendingSeries = uiState.trendingSeries ?: listOf(),
        recentRecommendations = uiState.recommendations ?: listOf(),
        onMediaItemClicked = onMediaItemClicked
    )
}

@Composable
private fun HomeScreenContent(
    searchQueryText: String? = null,
    onQueryTextChange: (queryText: String) -> Unit = {},
    onSearchTextSubmitted: (queryText: String) -> Unit = {},
    recentRecommendations: List<Recommendation>,
    trendingMovies: List<MediaItem>,
    trendingSeries: List<MediaItem>,
    onMediaItemClicked: (mediaItem: MediaItem) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        /**
         *  SEARCH SECTION
         * **/
        CustomSearchView(
            queryText = searchQueryText ?: "",
            onQueryTextChange = { onQueryTextChange(it) },
            onQueryTextSubmit = {
                if (it != "") {
                    onSearchTextSubmitted(it)
                }
            })


        /**
         *  Recent Recomendation
         * **/
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Recommendations",
                    style = Typography.titleMedium.copy(fontSize = 21.sp)
                )
                Icon(
                    modifier = Modifier.padding(start = 16.dp),
                    imageVector = Icons.Outlined.Recommend,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "recommend icon"
                )
            }

            if (recentRecommendations.isEmpty()) {
                Text(
                    modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp),
                    text = "No recommendation received or sent so far",
                    style = Typography.bodyMedium.copy(fontSize = 16.sp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    itemsIndexed(recentRecommendations!!) { index, item ->
                        RecentRecommendationItemComposable(
                            item,
                            onMediaItemClicked = { onMediaItemClicked(item.mediaItem) })
                    }
                }
            }
        }

        /**
         *  TRENDING SECTION
         * **/

        /**
         *  TRENDING SECTION
         * **/
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Trending", style = Typography.titleMedium.copy(fontSize = 21.sp))
                Icon(
                    modifier = Modifier.padding(start = 16.dp),
                    imageVector = Icons.Outlined.TrendingUp,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "recommend icon"
                )
            }
            var tabIndex by remember { mutableStateOf(0) }

            val tabs = listOf("Movies", "Series")

            Column(modifier = Modifier.fillMaxWidth()) {
                TabRow(selectedTabIndex = tabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(title)
                            },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index },
                        )
                    }
                }
                when (tabIndex) {
                    0 -> { // Horizontal List of movies
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            itemsIndexed(trendingMovies) { index, item ->
                                MovieItemComposable(item, onMediaItemClicked = {
                                    onMediaItemClicked(item)
                                })
                            }
                        }
                    }

                    1 -> { // Horizontal list of series
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            itemsIndexed(trendingSeries) { index, item ->
                                SeriesItemComposable(
                                    item,
                                    onMediaItemClicked = { onMediaItemClicked(item) })
                            }
                        }
                    }
                }
            }


        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    FilmDrawerTheme {
        HomeScreenContent(
            trendingMovies = dummyListOfMovie,
            trendingSeries = dummyListOfSeries,
            recentRecommendations = listOf()
        )
    }
}