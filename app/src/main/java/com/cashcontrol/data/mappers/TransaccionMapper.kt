package com.cashcontrol.data.mappers

import com.cashcontrol.data.local.entities.TransaccionEntity
import com.cashcontrol.data.remote.dto.TransaccionRequestDto
import com.cashcontrol.data.remote.dto.TransaccionResponseDto
import java.util.Date

fun TransaccionResponseDto.toEntity() = TransaccionEntity(
    transaccionId = this.transaccionId,
    usuarioId = this.usuarioId,
    categoriaId = this.categoriaId,
    monto = this.monto,
    fechaTransaccion = this.fechaTransaccion,
    usarSugerencia = this.usarSugerencia,
    descripcion = this.descripcion
)

fun TransaccionRequestDto.toEntity() = TransaccionEntity(
    transaccionId = this.transaccionId,
    usuarioId = this.usuarioId,
    categoriaId = this.categoriaId,
    monto = this.monto,
    usarSugerencia = this.usarSugerencia,
    descripcion = this.descripcion,
    fechaTransaccion = Date()
)

fun TransaccionEntity.toResponse() = TransaccionResponseDto(
    transaccionId = this.transaccionId ?: 0,
    usuarioId = this.usuarioId,
    categoriaId = this.categoriaId,
    monto = this.monto,
    fechaTransaccion = this.fechaTransaccion,
    usarSugerencia = this.usarSugerencia,
    descripcion = this.descripcion
)