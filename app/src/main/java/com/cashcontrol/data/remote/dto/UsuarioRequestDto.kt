package com.cashcontrol.data.remote.dto

import com.squareup.moshi.Json

data class UsuarioRequestDto(
    @Json(name = "usuarioId")
    val usuarioId: Long?,

    @Json(name = "nombre")
    val nombre: String?,

    @Json(name = "password")
    val contrasena: String?,

    @Json(name = "email")
    val email: String?,

    @Json(name = "fotoPath")
    val fotoPath: String?
)