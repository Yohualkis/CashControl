package com.cashcontrol.presentation.categoria

import com.cashcontrol.data.local.entities.CategoriaEntity

sealed interface CategoriaEvent {
    data class DescripcionChange(val descripcion: String): CategoriaEvent
    data class TipoCategoriaChange(val tipoCategoria: String): CategoriaEvent

    data class Delete(val categoria: CategoriaEntity): CategoriaEvent
    data object Save: CategoriaEvent
    data object Limpiar: CategoriaEvent
}