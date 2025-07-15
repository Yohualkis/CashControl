package com.cashcontrol.data.mappers

import com.cashcontrol.data.local.entities.UsuarioEntity
import com.cashcontrol.data.remote.dto.UsuarioRequestDto
import com.cashcontrol.data.remote.dto.UsuarioResponseDto

fun UsuarioRequestDto.toEntity() = UsuarioEntity(
    usuarioId = usuarioId,
    nombre = nombre ?: "",
    email = email ?: "",
    fotoPath = fotoPath,
    fechaRegistro = null
)

fun UsuarioResponseDto.toEntity() = UsuarioEntity(
    usuarioId = usuarioId,
    nombre = nombre ?: "",
    email = email ?: "",
    fotoPath = fotoPath,
    fechaRegistro = fechaRegistro
)

fun UsuarioEntity.toResponse() = UsuarioResponseDto(
    usuarioId = this.usuarioId,
    nombre = this.nombre,
    email = this.email,
    fechaRegistro = this.fechaRegistro,
    fotoPath = this.fotoPath
)