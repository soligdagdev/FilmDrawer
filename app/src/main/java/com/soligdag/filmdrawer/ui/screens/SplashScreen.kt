package com.soligdag.filmdrawer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soligdag.filmdrawer.R
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import com.soligdag.filmdrawer.ui.viewmodels.SplashViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel = hiltViewModel(),  onDataReceived : (isLoggedIn : Boolean, isEmailVerified : Boolean) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    if(uiState.hasDataReceived) {

        LaunchedEffect(Unit) {
            onDataReceived(uiState.isLoggedIn, uiState.isEmailVerified)
        }
    }

}

@Composable
fun SplashScreenContent() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center
    ) {

        Box(
            Modifier
                .fillMaxWidth(1f)
                .height(420.dp)
        ) {

//            val gradient = Brush.verticalGradient(
//                colors = listOf(
//                    MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
//                    MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
//                    MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
//                    MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
//                    MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
//                    MaterialTheme.colorScheme.background,
//                ),
//                startY = 0f,  // 1/3
//                endY = LocalDensity.current.run { 420.dp.toPx() }
//            )
//            Box(
//                modifier = Modifier
//                    .matchParentSize()
//                    .background(gradient)
//            )
        }
    }
}

@Preview (showBackground = true)
@Composable
fun SplashScreenPreview() {
    FilmDrawerTheme(darkTheme = true) {
        SplashScreenContent()
    }

}