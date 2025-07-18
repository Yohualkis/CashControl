package com.cashcontrol.presentation.navigation

import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cashcontrol.presentation.autorizacion.login.LoginScreen
import com.cashcontrol.presentation.autorizacion.register.RegisterScreen
import com.cashcontrol.presentation.dashboard.DashboardScreen

@Composable
fun CashControlNavHost(
    nav: NavHostController,
) {
    NavHost(
        navController = nav,
        startDestination = Screen.Login
    ) {

        // LOGIN
        composable<Screen.Login> {
            LoginScreen(
                goToDash = {
                    nav.navigate(Screen.Dashboard) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
                goToRegister = {
                    nav.navigate(Screen.Register){
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
            )
        }

        // REGISTER
        composable<Screen.Register> {
            RegisterScreen(
                goToLogin = {
                    nav.navigate(Screen.Login){
                        popUpTo(Screen.Register) { inclusive = true }
                    }
                }
            )
        }

        // DASHBOARD
        composable<Screen.Dashboard> {
            DashboardScreen(
                goToDashboard = {},
                goToSugerencias = {}
            )
        }
    }
}