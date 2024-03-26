package com.soligdag.filmdrawer.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soligdag.filmdrawer.data.models.Friend
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography


/** Screen Definition And Purpose
 *
 */
@Composable
fun ShareBottomSheetScreen(mediaName : String, friends : List<Friend>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Recommend $mediaName to",
            style = Typography.titleMedium.copy(fontSize = 20.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(16.dp)
        )
        if(friends.isNotEmpty()) {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(friends) { friend ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            // width is handled by LazyVerticalGrid
                            // it is important for you to specify the height.
                            .height(120.dp),
                        // here you change the background with each color from the list
                    ) {
                        // add your content
                    }
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ShareBottomSheetScreenPreview() {
    FilmDrawerTheme() {
        ShareBottomSheetScreen(mediaName = "Willy Wonka", friends = listOf())
    }

}