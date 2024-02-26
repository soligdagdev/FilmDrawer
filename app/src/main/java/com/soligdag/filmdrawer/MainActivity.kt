package com.soligdag.filmdrawer

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.soligdag.filmdrawer.ui.screens.LoginScreen
import com.soligdag.filmdrawer.ui.screens.MovieDetailScreen
import com.soligdag.filmdrawer.ui.screens.SearchScreen
import com.soligdag.filmdrawer.ui.screens.SeriesDetailScreen
import com.soligdag.filmdrawer.ui.screens.ShareMediaScreen
import com.soligdag.filmdrawer.ui.screens.SignupScreen
import com.soligdag.filmdrawer.ui.screens.WishlistScreen
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint
import io.branch.referral.Branch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Branch logging for debugging
        Branch.enableLogging()
        // Branch object initialization
        Branch.getAutoInstance(this)

        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            FilmDrawerTheme {
                // A surface container using the 'background' color from the theme
                Surface {
//                    val view = LocalView.current
//                    val isDarkTheme = isSystemInDarkTheme()
//                    SideEffect {
//                        window.statusBarColor = Color.Transparent.toArgb() // change color status bar here
//                        WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars = !isDarkTheme
//                    }
                    MainComposable()
                    //SignupScreen()
                    //SearchScreen("")
                    //MovieDetailScreen(movieId = 9502)
                    //SeriesDetailScreen(seriesId = 60622)
                    //WishlistScreen(onItemClicked = {})
                    //ShareMediaScreen()
                }

            }
        }
    }


    override fun onStart() {
        super.onStart()
        Branch.sessionBuilder(this).withCallback { branchUniversalObject, linkProperties, error ->
            if (error != null) {
                Log.e("BranchSDK_Tester", "branch init failed. Caused by -" + error.message)
            } else {
                Log.i("BranchSDK_Tester", "branch init complete!")
                if (branchUniversalObject != null) {
                    Log.i("BranchSDK_Tester", "title " + branchUniversalObject.title)
                    Log.i("BranchSDK_Tester", "CanonicalIdentifier " + branchUniversalObject.canonicalIdentifier)
                    Log.i("BranchSDK_Tester", "metadata " + branchUniversalObject.contentMetadata.convertToJson())
                }
                if (linkProperties != null) {
                    Log.i("BranchSDK_Tester", "Channel " + linkProperties.channel)
                    Log.i("BranchSDK_Tester", "control params " + linkProperties.controlParams)
                }
            }
        }.withData(this.intent.data).init()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.setIntent(intent);
        if (intent != null && intent.hasExtra("branch_force_new_session") && intent.getBooleanExtra("branch_force_new_session",false)) {
            Branch.sessionBuilder(this).withCallback { referringParams, error ->
                if (error != null) {
                    Log.e("BranchSDK_Tester", error.message)
                } else if (referringParams != null) {
                    Log.i("BranchSDK_Tester", referringParams.toString())
                }
            }.reInit()
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