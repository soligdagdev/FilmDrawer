package com.soligdag.filmdrawer.ui.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.soligdag.filmdrawer.ui.screens.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComposable() {
    val navController = rememberNavController()
    var bottomNavSelectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, navDestination, _ ->
        Log.d("Navigated to ", navDestination.route?:"")
        when(navDestination.route) {
            Destination.Home.route -> bottomNavSelectedIndex = 0
            Destination.Recommendations.route -> bottomNavSelectedIndex = 1
            Destination.Wishlist.route -> bottomNavSelectedIndex = 2
            Destination.Profile.route -> bottomNavSelectedIndex = 3
        }
        if(navDestination.route == Destination.Home.route) {
            bottomNavSelectedIndex = 0
        }
        else if(navDestination.route == Destination.Wishlist.route) {
            bottomNavSelectedIndex = 2
        }
    })
    Scaffold(
        bottomBar = {
            BottomNavigation(navController, bottomNavSelectedIndex, onBottomNavIndexUpdated = { bottomNavSelectedIndex = it})
        }, contentWindowInsets = WindowInsets(0.dp,0.dp,0.dp,0.dp)
    ) {
        RootNavigation(navController = navController, paddingValues = it)
    }
}

//val homeBottomNavigation =
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigation(navController: NavHostController, bottomNavSelectedIndex : Int, onBottomNavIndexUpdated : (index : Int)-> Unit) {
    val bottomNavDestinations = Destination.getBottomNavigationDestinations()

    AnimatedVisibility(visible = bottomBarVisibility(navController).value) {
        NavigationBar() {
            bottomNavDestinations.forEachIndexed { index, destination ->
                NavigationBarItem(
                    selected = index == bottomNavSelectedIndex,
                    icon = {
                        Icon(
                            imageVector = destination.icon ?: Icons.Filled.Home,
                            contentDescription = destination.title
                        )
                    },
                    onClick = {
                        //bottomNavSelectedIndex = index
                        onBottomNavIndexUpdated(index)
                        navController.navigate(destination.route) {
                            popUpTo(Destination.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }

    }
}

@Composable
private fun bottomBarVisibility(navController: NavController): MutableState<Boolean> {
    val visibilityState = rememberSaveable { mutableStateOf(false) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    when (backStackEntry?.destination?.route) {
        Destination.Home.route -> visibilityState.value = true
        Destination.Recommendations.route -> visibilityState.value = true
        Destination.Wishlist.route -> visibilityState.value = true
        Destination.Profile.route -> visibilityState.value = true
        else -> visibilityState.value = false

    }
    return visibilityState
}

@Composable
fun RootNavigation(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = Destination.Splash.route, modifier= Modifier.padding(paddingValues)) {
        composable(Destination.Splash.route) {
            SplashScreen(onDataReceived = { isLoggedIn, isEmailVerified ->
                if (!isLoggedIn) {
                    navController.navigate(Routes.PRELOGIN) {
                        popUpTo(Destination.Splash.route) {
                            inclusive = true
                        }
                    }
                } else if(!isEmailVerified) {
                    navController.navigate("preLoginRoute/emailVerificationScreen") {
                        popUpTo(Destination.Splash.route) {
                            inclusive = true
                        }
                    }
                }else {
                    navController.navigate(Routes.POSTLOGIN) {
                        popUpTo(Destination.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            })
        }
        preLoginNavigation(navController)
        postLoginNavigation(navController)
    }
}


object Routes {
    const val PRELOGIN: String = "preLoginRoute"
    const val POSTLOGIN: String = "postLoginRoute"
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive =  true
    }
}