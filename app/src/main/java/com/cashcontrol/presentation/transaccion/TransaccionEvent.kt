package com.cashcontrol.presentation.transaccion

import com.cashcontrol.data.local.entities.TransaccionEntity

sealed interface TransaccionEvent {
    data class CategoriaChange(val categoriaId: Long?, val descripcion: String): TransaccionEvent
    data class DescripcionTransaccionChange(val descripcionTransaccion: String): TransaccionEvent
    data class UsarEnSugerenciaChange(val usarEnSugerenciaChange :Boolean): TransaccionEvent
    data class MontoChange(val monto: Double): TransaccionEvent

    data class GetTransacciones(val tipoCategoria: String): TransaccionEvent
    data class Delete(val transaccion: TransaccionEntity): TransaccionEvent
    data object Save: TransaccionEvent
    data object Limpiar: TransaccionEvent
}