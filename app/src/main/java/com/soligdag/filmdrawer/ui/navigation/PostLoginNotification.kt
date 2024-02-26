package com.soligdag.filmdrawer.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.ui.screens.ActorDetailScreen
import com.soligdag.filmdrawer.ui.screens.FriendDetailScreen
import com.soligdag.filmdrawer.ui.screens.FriendsListScreen
import com.soligdag.filmdrawer.ui.screens.HomeScreen
import com.soligdag.filmdrawer.ui.screens.MovieDetailScreen
import com.soligdag.filmdrawer.ui.screens.ProfileScreen
import com.soligdag.filmdrawer.ui.screens.RecommendationsListScreen
import com.soligdag.filmdrawer.ui.screens.SearchScreen
import com.soligdag.filmdrawer.ui.screens.SeriesDetailScreen
import com.soligdag.filmdrawer.ui.screens.TrendingsListScreen
import com.soligdag.filmdrawer.ui.screens.WishlistScreen

fun NavGraphBuilder.postLoginNavigation(navController: NavController) {
    navigation(startDestination = Destination.Home.route, route = Routes.POSTLOGIN) {
        composable(route = Destination.Home.route) {
            HomeScreen(onMediaItemClicked = { mediaItem: MediaItem ->
                if (mediaItem.mediaType == "movie") {
                    navController.navigate(route = "movieDetailScreen/"+mediaItem.id)
                }
                else {
                    navController.navigate(route = "seriesDetailScreen/"+mediaItem.id)
                }
            }, onSearchTextSubmitted = { searchText -> String
                navController.navigate(route = "searchScreen/"+searchText)
            })
        }
        composable(Destination.Recommendations.route) {
            RecommendationsListScreen()
        }
        composable(Destination.Wishlist.route) {
            WishlistScreen(onItemClicked = { wishlistItem ->
                if (wishlistItem.mediaType == "movie") {
                    navController.navigate(route = "movieDetailScreen/"+wishlistItem.id)
                }
                else {
                    navController.navigate(route = "seriesDetailScreen/"+wishlistItem.id)
                }
            })
        }
        composable(Destination.Profile.route) {
            ProfileScreen(onUserLoggedOut = {
                navController.navigate(Routes.PRELOGIN) {
                    popUpTo(Routes.POSTLOGIN){
                        inclusive = true
                    }
                }
            })
        }
        composable(Destination.Search.route, arguments = listOf(navArgument("searchText") {type = NavType.StringType})) {
            SearchScreen(queryText = it.arguments?.getString("searchText")?:"", onMediaItemClicked = { mediaItem: MediaItem ->
                if (mediaItem.mediaType == "movie") {
                    navController.navigate(route = "movieDetailScreen/"+mediaItem.id)
                }
                else {
                    navController.navigate(route = "seriesDetailScreen/"+mediaItem.id)
                }
            })
        }
        composable(Destination.MovieDetail.route , arguments = listOf(navArgument("id") {type = NavType.IntType})) {
            MovieDetailScreen(movieId = it.arguments?.getInt("id")?:0, onAddedToWishList = {
                navController.navigate(route = Destination.Wishlist.route) {
                    popUpTo(Destination.Home.route) {
                        inclusive = true
                    }
                }
            }, onBackBtnPressed = {navController.popBackStack() })
        }
        composable(Destination.SeriesDetail.route , arguments = listOf(navArgument("id") {type = NavType.IntType})) {
            SeriesDetailScreen(seriesId = it.arguments?.getInt("id")?:0, onAddedToWishList = {
                navController.navigate(route = Destination.Wishlist.route) {
                    popUpTo(Destination.Home.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }, onBackBtnPressed = { navController.popBackStack() })
        }
        composable(Destination.ActorDetail.route) {
            ActorDetailScreen()
        }
        composable(Destination.TrendingList.route) {
            TrendingsListScreen()
        }
        composable(Destination.FriendsList.route) {
            FriendsListScreen()
        }
        composable(Destination.FriendDetail.route) {
            FriendDetailScreen()
        }
    }
}