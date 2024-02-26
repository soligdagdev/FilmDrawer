package com.soligdag.filmdrawer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import com.soligdag.filmdrawer.ui.viewmodels.ProfileScreenViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    onUserLoggedOut: () -> Unit = {}
) {
    ProfileScreenContent(onLogoutClicked = {
        viewModel.logoutUser()
        onUserLoggedOut()
    })
}

@Composable
fun ProfileScreenContent(onLogoutClicked: () -> Unit = {}) {
    Column(Modifier.fillMaxSize(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { onLogoutClicked() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(0.dp, 64.dp)
                .height(50.dp)
        ) {
            Text(text = "Log out", style = Typography.bodyMedium)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    FilmDrawerTheme() {
        ProfileScreenContent()
    }
}