package com.soligdag.filmdrawer

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.soligdag.filmdrawer.ui.navigation.Destination
import com.soligdag.filmdrawer.ui.navigation.MainComposable
import com.soligdag.filmdrawer.ui.screens.HomeScreen
import com.soligdag.filmdrawer.ui.screens.MovieDetailScreen
import com.soligdag.filmdrawer.ui.screens.SearchScreen
import com.soligdag.filmdrawer.ui.screens.SeriesDetailScreen
import com.soligdag.filmdrawer.ui.screens.WishlistScreen
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContent {
            FilmDrawerTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    val view = LocalView.current
                    val isDarkTheme = isSystemInDarkTheme()
                    SideEffect {
                        window.statusBarColor = Color.Transparent.toArgb() // change color status bar here
                        WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars = !isDarkTheme
                    }
                    MainComposable()
                    //SearchScreen("")
                    //MovieDetailScreen(movieId = 9502)
                    //SeriesDetailScreen(seriesId = 60622)
                    //WishlistScreen(onItemClicked = {})
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        style = Typography.displayLarge,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FilmDrawerTheme {
        Greeting("Android")
    }
}