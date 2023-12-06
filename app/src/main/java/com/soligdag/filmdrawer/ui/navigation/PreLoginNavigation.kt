package com.soligdag.filmdrawer.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soligdag.filmdrawer.ui.screens.EmailVerificationScreen
import com.soligdag.filmdrawer.ui.screens.LoginScreen
import com.soligdag.filmdrawer.ui.screens.SignupScreen


fun NavGraphBuilder.preLoginNavigation(navController: NavController) {
    navigation(startDestination = Destination.Login.route, route = Routes.PRELOGIN) {
        composable(Destination.Login.route) {
            LoginScreen()
        }
        composable(Destination.Signup.route) {
            SignupScreen()
        }
        composable(Destination.EmailVerification.route) {
            EmailVerificationScreen()
        }
    }
}



