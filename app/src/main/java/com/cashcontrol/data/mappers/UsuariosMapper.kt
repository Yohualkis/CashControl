package com.cashcontrol.data.mappers

import com.cashcontrol.data.local.entities.UsuarioEntity
import com.cashcontrol.data.remote.dto.UsuarioDto

fun UsuarioDto.toEntity(): UsuarioEntity {
    return UsuarioEntity(
        usuarioId = usuarioId,
        nombre = nombre ?: "",
        email = email ?: "",
        fotoPath = fotoPath,
        fechaRegistro = fechaRegistro
    )
}