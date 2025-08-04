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

    @Serializable
    data object ListaCategorias: Screen()

    @Serializable
    data class Categoria(val categoriaId: Long?): Screen()

    @Serializable
    data object ListaTransacciones: Screen()

    @Serializable
    data class Transaccion(val transaccionId: Long?): Screen()
}