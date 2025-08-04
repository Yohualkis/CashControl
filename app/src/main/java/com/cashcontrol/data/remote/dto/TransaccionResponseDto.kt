package com.cashcontrol.data.remote.dto

import java.util.Date

data class TransaccionResponseDto(
    val transaccionId: Long,
    val categoriaId: Long,
    val usuarioId: Long,
    val monto: Double,
    val fechaTransaccion: Date,
    val usarSugerencia: Boolean,
    val descripcion: String,
)
