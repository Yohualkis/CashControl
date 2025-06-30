package com.cashcontrol.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cashcontrol.presentation.signInOrOut.LoginScreen

@Composable
fun CashControlNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login
    ) {
        // Login
        composable<Screen.Login> {
            LoginScreen(nav = navHostController)
        }
    }
}