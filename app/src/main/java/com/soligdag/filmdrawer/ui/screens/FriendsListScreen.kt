package com.soligdag.filmdrawer.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme


/** Screen Definition And Purpose
 *
 */
@Composable
fun FriendsListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Friends List Screen", modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun FriendsListScreenPreview() {
    FilmDrawerTheme {
        FriendsListScreen()
    }

}