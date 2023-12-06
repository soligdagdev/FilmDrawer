package com.soligdag.filmdrawer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.soligdag.filmdrawer.FilmDrawerApplication
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.dummyListOfMovie
import com.soligdag.filmdrawer.data.models.dummyListOfSeries
import com.soligdag.filmdrawer.data.models.dummyMovieRecommendation
import com.soligdag.filmdrawer.ui.components.CustomSearchView
import com.soligdag.filmdrawer.ui.components.MovieItemComposable
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.ui.components.RecentRecommendationItemComposable
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import com.soligdag.filmdrawer.ui.viewmodels.HomeViewModel
import com.soligdag.filmdrawer.ui.viewmodels.viewModelFactory

@Composable
fun HomeScreen(
    viewModel: HomeViewModel =
        viewModel(factory = viewModelFactory { HomeViewModel(FilmDrawerApplication.container.mediaRepository) },
            ),
    onMediaItemClicked : (mediaItem: MediaItem) -> Unit = { },
    onSearchTextSubmitted : (queryText: String) -> Unit = { }
) {
    val searchQuery by viewModel.searchQuery.observeAsState()
    HomeScreenContent(
        searchQueryText = searchQuery,
        onQueryTextChange = {
            viewModel.onSearchQueryChange(newText = it)
        },
        onSearchTextSubmitted = { onSearchTextSubmitted (it)},
        trendingMovies = dummyListOfMovie,
        trendingSeries = dummyListOfSeries,
        recentRecommendations = listOf(dummyMovieRecommendation),
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
    trendingSeries : List<MediaItem>,
    onMediaItemClicked : (mediaItem: MediaItem) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
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

        /**
         *  Recent Recomendation
         * **/
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)) {
            Text(text = "Recent Recommendations", style = Typography.titleMedium.copy(fontSize = 21.sp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                itemsIndexed(recentRecommendations!!) { index, item ->
                    RecentRecommendationItemComposable(item, onMediaItemClicked = { onMediaItemClicked(item.mediaItem) })
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
            Text(text = "Trending", style = Typography.titleMedium.copy(fontSize = 21.sp))
            var tabIndex by remember { mutableStateOf(0) }

            val tabs = listOf("Movies", "Series")

            Column(modifier = Modifier.fillMaxWidth()) {
                TabRow(selectedTabIndex = tabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
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
                                MovieItemComposable(item, onMediaItemClicked =  { onMediaItemClicked(item) })
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
        HomeScreenContent(trendingMovies = dummyListOfMovie, trendingSeries = dummyListOfSeries, recentRecommendations = listOf(dummyMovieRecommendation))
    }
}