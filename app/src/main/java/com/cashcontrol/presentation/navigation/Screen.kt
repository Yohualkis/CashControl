package com.cashcontrol.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Login: Screen()

    @Serializable
    data object Register: Screen()

    @Serializable
    data object Dashboard: Screen()

    @Serializable
    data object Sugerencias: Screen()
}