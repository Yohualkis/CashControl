package com.cashcontrol.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cashcontrol.presentation.autorizacion.login.LoginScreen
import com.cashcontrol.presentation.autorizacion.register.RegisterScreen
import com.cashcontrol.presentation.categoria.CategoriaListScreen
import com.cashcontrol.presentation.categoria.CategoriaScreen
import com.cashcontrol.presentation.composables.AppBottomBar
import com.cashcontrol.presentation.dashboard.DashboardScreen
import com.cashcontrol.presentation.transaccion.TransaccionListScreen
import com.cashcontrol.presentation.transaccion.TransaccionScreen

@Composable
fun CashControlNavHost(
    nav: NavHostController,
) {
    val currentScreen by remember {
        derivedStateOf {
            when (nav.currentDestination?.route?.substringBefore("/")) {
                "dashboard" -> Screen.Dashboard
                "categorias" -> Screen.ListaCategorias
                "transacciones" -> Screen.ListaTransacciones
                "sugerencias" -> Screen.Sugerencias
                else -> Screen.Dashboard
            }
        }
    }
    Scaffold(
        bottomBar = {
            // Solo muestra en pantallas principales
            when (currentScreen) {
                is Screen.Login, is Screen.Register -> Unit // No mostrar
                else -> AppBottomBar(
                    pantallaActual = currentScreen,
                    onNavigate = { screen ->
                        when (screen) {
                            is Screen.Dashboard -> nav.navigate(Screen.Dashboard)
                            is Screen.ListaTransacciones -> nav.navigate(Screen.ListaTransacciones)
                            is Screen.ListaCategorias -> nav.navigate(Screen.ListaCategorias)
                            is Screen.Sugerencias -> nav.navigate(Screen.Sugerencias)
                            else -> {}
                        }
                    },
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = nav,
            startDestination = Screen.Login,
            modifier = Modifier.padding(innerPadding)
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
                        nav.navigate(Screen.Register) {
                            popUpTo(Screen.Login) { inclusive = true }
                        }
                    },
                )
            }

            // REGISTER
            composable<Screen.Register> {
                RegisterScreen(
                    goToLogin = {
                        nav.navigate(Screen.Login) {
                            popUpTo(Screen.Register) { inclusive = true }
                        }
                    }
                )
            }

            // DASHBOARD
            composable<Screen.Dashboard> {
                DashboardScreen(
                    goToDashboard = {},
                    goToSugerencias = {},
                )
            }

            // CATEGORIAS
            composable<Screen.ListaCategorias> {
                CategoriaListScreen(
                    goToCategoria = { nav.navigate(Screen.Categoria(it)) },
                    goBack = { nav.navigateUp() },
                )
            }

            composable<Screen.Categoria> { backStack ->
                val args = backStack.toRoute<Screen.Categoria>()
                CategoriaScreen(
                    categoriaId = args.categoriaId,
                    goBack = { nav.navigateUp() },
                )
            }
            
            // TRANSACCIONES
            composable<Screen.ListaTransacciones> {
                TransaccionListScreen(
                    goToTransaccion = { nav.navigate(Screen.Transaccion(it)) },
                    goBack = { nav.navigateUp() },
                )
            }

            composable<Screen.Transaccion> { backStack ->
                val args = backStack.toRoute<Screen.Transaccion>()
                TransaccionScreen(
                    transaccionId = args.transaccionId,
                    goBack = { nav.navigateUp() },
                )
            }
        }
    }
}