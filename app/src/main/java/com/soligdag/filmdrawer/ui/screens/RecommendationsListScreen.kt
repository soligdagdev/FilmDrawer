package com.soligdag.filmdrawer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soligdag.filmdrawer.data.models.dummyMovieRecommendation
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.ui.components.RecommendationItemComposable
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme

@Composable
fun RecommendationsListScreen() {
    recommendationsListContent(receivedRecommendations = listOf( dummyMovieRecommendation), sentRecommendations = listOf( dummyMovieRecommendation))
}

@Composable
private fun recommendationsListContent(receivedRecommendations : List<Recommendation>, sentRecommendations : List<Recommendation>) {

    Column(modifier = Modifier.fillMaxSize().padding(top = 42.dp)) {
        var tabIndex by remember { mutableStateOf(0) }
        val tabs = listOf("Received", "Sent")
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
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        itemsIndexed(receivedRecommendations) { index, item ->
                            RecommendationItemComposable(modifier = Modifier, item)
                        }
                    }
                }

                1 -> { // Horizontal list of series
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        itemsIndexed(sentRecommendations) { index, item ->
                            RecommendationItemComposable(modifier = Modifier, recommendation = item)
                        }
                    }
                }
            }
        }
    }

}


@Preview (showSystemUi = true)
@Composable
fun recommendationsListPreview() {
    FilmDrawerTheme {
        recommendationsListContent(listOf( dummyMovieRecommendation), listOf( dummyMovieRecommendation))
    }
}
