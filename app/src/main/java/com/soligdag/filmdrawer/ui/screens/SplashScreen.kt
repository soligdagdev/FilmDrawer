package com.soligdag.filmdrawer.ui.screens

import android.window.SplashScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography

@Composable
fun SplashScreen(onDataReceived : (isLoggedIn : Boolean) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Text(text = "Splash Screen", style = Typography.displayLarge)
    }
}

@Preview (showBackground = true)
@Composable
fun SplashScreenPreview() {
    FilmDrawerTheme {
        SplashScreen(onDataReceived = {})
    }

}