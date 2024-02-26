package com.soligdag.filmdrawer.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.soligdag.filmdrawer.ui.screens.EmailVerificationScreen
import com.soligdag.filmdrawer.ui.screens.LoginScreen
import com.soligdag.filmdrawer.ui.screens.SignupScreen


fun NavGraphBuilder.preLoginNavigation(navController: NavController) {
    navigation(startDestination = Destination.Login.route, route = Routes.PRELOGIN) {
        composable(Destination.Login.route) {
            LoginScreen(onSignupClicked = { navController.navigate(Destination.Signup.route)}, onLoginSuccessfully = { isEmailVerified -> Boolean
                if(isEmailVerified) { // Load home Screen
                    navController.navigate(Routes.POSTLOGIN) {
                        popUpTo(Destination.Login.route){ inclusive = true }
                    }
                }
                else {
                    navController.navigate(Destination.EmailVerification.route) { popUpTo(Destination.Login.route) { inclusive = true } }
                }
            })
        }
        composable(Destination.Signup.route) {
            SignupScreen(onLoginClicked = {navController.popBackStack()}, onSignedUp = { navController.navigate(Destination.EmailVerification.route) { popUpTo(Destination.Login.route) { inclusive = true } } })
        }
        composable(Destination.EmailVerification.route, deepLinks = listOf(navDeepLink {
            uriPattern = Destination.emailVerificationDeepLink
        })) {
            EmailVerificationScreen()
        }
    }
}



