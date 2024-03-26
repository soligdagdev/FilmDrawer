package com.soligdag.filmdrawer.ui.navigation

import android.text.BoringLayout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination (val route : String, val title : String? = null, val icon : ImageVector? = null) {
         object Splash : Destination (route = "splashScreen")
         object Login : Destination (route = "loginScreen")
         object Signup : Destination (route = "signupScreen")
         object EmailVerification : Destination (route = "emailVerificationScreen")

         object Home : Destination (route = "homeScreen", title = "Home", icon = Icons.Filled.Home)
         object Recommendations : Destination (route = "recommendationsScreen", title = "Recommendations", icon = Icons.Filled.Badge)
         object Wishlist : Destination (route = "wishlistScreen", title = "Wishlist", icon = Icons.Filled.List)
         object Profile : Destination (route = "profileScreen", title = "My profile", icon = Icons.Filled.Person)

         object Search : Destination (route = "searchScreen/{searchText}")
         object MovieDetail : Destination (route = "movieDetailScreen/{id}")
         object SeriesDetail : Destination (route = "seriesDetailScreen/{id}")
         object ActorDetail : Destination (route = "actorDetailScreen/{id}")
         object TrendingList : Destination (route = "trendingListScreen")
         object FriendsList : Destination (route = "friendsListScreen")
         object FriendDetail : Destination (route = "friendDetailScreen/{id}")
         object ShareMedia : Destination (route = "shareMediaScreen/{id}/{mediaType}")

        companion object {
            fun getBottomNavigationDestinations() : List<Destination> {
                return listOf(Home,Recommendations,Wishlist,Profile)
            }

            const val emailVerificationDeepLink = "android-app://androidx.navigation/preLoginRoute/emailVerificationScreen"


        }

}