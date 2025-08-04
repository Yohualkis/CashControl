package com.cashcontrol.data.remote.dto

data class TransaccionRequestDto(
    val transaccionId: Long,
    val categoriaId: Long,
    val usuarioId: Long,
    val monto: Double,
    val usarSugerencia: Boolean,
    val descripcion: String,
)
